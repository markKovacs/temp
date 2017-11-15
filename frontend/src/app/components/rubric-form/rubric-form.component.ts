import {Component, Input, OnInit} from '@angular/core';
import {RubricService} from "../../_services/rubric.service";
import {Rubric} from "../../_models/screening/rubric";

@Component({
  selector: 'app-rubric-form',
  templateUrl: './rubric-form.component.html',
  styleUrls: ['./rubric-form.component.css']
})
export class RubricFormComponent implements OnInit {

  @Input('criteriaId') criteriaId : string;
  rubrics: Rubric[];
  error: boolean;

  constructor( private rubricService: RubricService) { }

  ngOnInit() {
      this.getRubrics()
  }

  getRubrics(){
      this.rubricService.getRubrics(this.criteriaId).subscribe(
          (data: Rubric[]) => {
              console.log(data);
              this.rubrics = data;
          }
      )
  }

    addNewRubric() {
        let rubric = new Rubric();
        rubric.criteriaId = this.criteriaId;
        rubric.id = 0;
        rubric.order = this.calculateNewOrderNumber();
        this.rubrics.push(rubric);
    }

    deleteRubric(rubric: Rubric) {
        const dialogBox = confirm('Are you sure to remove this rubric');

        if (dialogBox) {
            let index = this.rubrics.indexOf(rubric);
            if (rubric.id) {
                this.rubricService.deleteRubric(rubric).subscribe(
                    (data: boolean) => {
                        this.rubrics.splice(index, 1);
                    }
                )
            }else {
                this.rubrics.splice(index, 1);
            }

        }

    }


    saveRubrics() {
        if (this.checkInputsAreValid()){
            this.rubricService.saveRubrics(this.rubrics).subscribe(
                (data: boolean) => {
                    alert("save success");
                    this.getRubrics();
                },
                (error: any) => {
                console.log(error)
            }
            );
        }

    }

    checkInputsAreValid(){

        for (let rubric of this.rubrics) {
            if (!rubric.text || (rubric.text.length === 0 || !rubric.text.trim())){
                this.error = true;
                return false;
            }
        }
        this.error = false;
        return true;
    }

    private calculateNewOrderNumber(): number{
        return this.rubrics[this.rubrics.length - 1].order + 1;
    }
}
