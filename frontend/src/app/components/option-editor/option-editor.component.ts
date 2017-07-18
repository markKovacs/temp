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

    newOption(): void{
        this.option = new Option;
        console.log(this.option);
    }

    ngOnInit(): void {
       // this.option.push({option: "string", isCorrect: true});
    }
}