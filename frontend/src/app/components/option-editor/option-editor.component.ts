import {Component, OnInit, Input} from "@angular/core";
import {Option} from "../../_models/survey/option.model";

@Component({
    moduleId: module.id,
    templateUrl: 'option-editor.component.html',
    styleUrls: ['option-editor.component.css'],
    selector: 'option-editor'

})
export class OptionEditorComponent implements OnInit{

    @Input() option: Option;
    @Input() i: number;

    ngOnInit(): void {
        if (this.option.isCorrect == null){
            this.option.isCorrect = false;
        }
    }
    deleteOption(): void{
        this.option = null;
    }

    getNumber(){
        return this.i + 1 + "";
    }
}