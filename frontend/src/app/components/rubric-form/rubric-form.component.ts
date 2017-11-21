import {Component, Input} from '@angular/core';
import {RubricService} from "../../_services/rubric.service";
import {Rubric} from "../../_models/screening/rubric";
import {Criteria} from "../../_models/screening/criteria";

@Component({
    selector: 'app-rubric-form',
    templateUrl: './rubric-form.component.html',
    styleUrls: ['./rubric-form.component.css']
})
export class RubricFormComponent {

    @Input('criteria') criteria: Criteria;
    rubrics: Rubric[] = [];
    error: boolean;

    danishScale = [-3,0,2,4,7,10,12];

    display = false;

    constructor(private rubricService: RubricService) {
    }

    displayDialog(){
        this.display = !this.display;
        this.getRubrics()
    }

    onHide(){
        this.display = false;
    }

    getRubrics() {
        if(this.criteria && this.criteria.id) {
            this.rubricService.getRubrics(this.criteria.id).subscribe(
                (data: Rubric[]) => {
                    this.rubrics = data;
                }
            )
        }
    }

    addNewRubric() {
        let rubric = new Rubric();
        rubric.criteriaId = this.criteria.id;
        rubric.id = 0;
        rubric.order = this.calculateNewOrderNumber();
        this.rubrics.push(rubric);
    }

    deleteRubric(rubric: Rubric) {
        this.rubrics = this.rubrics.filter(r => r !== rubric);
    }


    saveRubrics() {
        if (this.checkInputsAreValid()) {
            this.rubricService.saveRubrics(this.rubrics).subscribe(
                (data: boolean) => {
                    alert("Saved successfully");
                },
                (error: any) => {
                    console.log(error)
                }
            );
        }

    }

    checkInputsAreValid() {

        for (let rubric of this.rubrics) {
            if (!rubric.text || (rubric.text.length === 0 || !rubric.text.trim())) {
                this.error = true;
                return false;
            }
        }
        this.error = false;
        return true;
    }

    private calculateNewOrderNumber(): number {
        return this.danishScale[this.rubrics.length];
    }
}
