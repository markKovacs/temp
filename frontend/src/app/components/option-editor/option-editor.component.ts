import {Component, OnInit, Input} from "@angular/core";
import {Option} from "../../_models/option.model";

@Component({
    moduleId: module.id,
    templateUrl: 'option-editor.component.html',
    styleUrls: ['option-editor.component.css'],
    selector: 'option-editor'

})
export class OptionEditorComponent implements OnInit{

    @Input() option: Option;


    ngOnInit(): void {
        if (this.option.isCorrect == null){
            this.option.isCorrect = false;
        }
    }
}