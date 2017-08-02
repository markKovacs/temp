import {Component} from '@angular/core';
import {HttpClient} from "../../_httpclient/httpclient";
import {GlobalEventsManager} from "../../global.eventsmanager";
import {Location, Template, PostResponse} from "../../_models/index";
import {Message} from 'primeng/primeng';
import Mustache from 'mustache/mustache';
import * as _ from 'lodash';

@Component({
    moduleId: module.id,
    templateUrl: 'template-editor.component.html',
    styleUrls: ['template-editor.component.css']
})
export class TemplateEditorComponent {

    public location: Location;
    public templates: Template[];
    public chosenTemplate: Template;
    public masterTemplate: Template;
    public messages: Message[] = [];
    public newKey: string;
    editingName: boolean = false;

    public options: Object = {
        immediateAngularModelUpdate: true
    };

    constructor(
        private client: HttpClient,
        private eventsManager: GlobalEventsManager)
    {
        this.eventsManager.showNavBar(true);
        this.getLocation()
        this.getTemplates()
    }

    getLocation(){
        this.location = JSON.parse(localStorage.getItem("chosenLocation"));
    }

    getTemplates(){
        this.client.get('/api/templates?location=' + this.location.id).subscribe(
            (templates: Template[]) => {
                this.templates = templates;
                for (let template of this.templates) {
                    template.model = template.model == null ? {} : JSON.parse(template.model);
                }
                this.masterTemplate = this.templates.filter(entry => entry.master)[0]
            }
        )
    }

    chooseTemplate(template){
        this.chosenTemplate = template;
    }

    getKeys(model){
        return _.keys(model)
    }

    addNewKey(){
        this.chosenTemplate.model[this.newKey] = null;
        this.newKey = null;
    }

    previewTemplate(template){
        let generated = Mustache.render(template, this.chosenTemplate.model);
        let generatedMaster = Mustache.render(this.masterTemplate.template, this.masterTemplate.model);
        let parts = generatedMaster.split("({[content]})");
        if(parts.length !== 2){
            this.messages.push({
                severity: 'error',
                summary: 'Error in Master template',
                detail: 'Please check the syntax and try again'
            });
        }
        let full = parts[0] + generated + parts[1];
        let newWindow = window.open("", "", "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=0, left=0");
        newWindow.document.body.innerHTML = full;
    }

    saveTemplate(template){
        let postData = Object.assign({}, template);
        for (let key in postData.model) {
            postData.model[key] = null;
        }
        postData.model = JSON.stringify(postData.model);

        this.client.post('/api/templates/save', postData).subscribe(
            (response: PostResponse) => {
                this.messages.push({
                    severity: 'success',
                    summary: 'Save completed',
                    detail: this.chosenTemplate.name
                });
                this.editingName = false;
            }
        )
    }

}
