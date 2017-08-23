import {Component} from '@angular/core';
import {HttpClient} from '../../_httpclient/httpclient';
import {Location, Template, PostResponse} from '../../_models/index';
import {Message} from 'primeng/primeng';
import Mustache from 'mustache/mustache';
import * as _ from 'lodash';
import {EmailTemplateService} from '../../_services/email-template.service';

@Component({
    moduleId: module.id,
    templateUrl: 'template-editor.component.html',
    styleUrls: ['template-editor.component.css']
})
export class TemplateEditorComponent {

    public location: Location;
    public templates: Template[];
    public masterTemplate: Template;
    public chosenTemplate: Template;
    public messages: Message[] = [];
    public newKey: string;
    editingName = false;

    public options: Object = {
        immediateAngularModelUpdate: true
    };

    constructor(private client: HttpClient,
                private emailTemplateService: EmailTemplateService) {
        this.location = JSON.parse(localStorage.getItem('chosenLocation'));
        this.emailTemplateService.getTemplates(this.location.id)
            .subscribe(
                (templates: Template[]) => {
                this.templates = templates;
                for (const template of this.templates) {
                    template.model = (template.model == null) ? {} : JSON.parse(template.model);
                }
                this.masterTemplate = this.templates.filter(element => element.master)[0];
            });
    }

    chooseTemplate(template) {
        this.chosenTemplate = template;
    }

    getKeys(model) {
        return _.keys(model)
    }

    addNewKey(){
        this.chosenTemplate.model[this.newKey] = null;
        this.newKey = null;
    }

    previewTemplate(template){
        const generated = Mustache.render(template, this.chosenTemplate.model);
        const generatedMaster = Mustache.render(this.masterTemplate.template, this.masterTemplate.model);
        const parts = generatedMaster.split('({[content]})');
        if (parts.length !== 2){
            this.messages.push({
                severity: 'error',
                summary: 'Error in Master template',
                detail: 'Please check the syntax and try again'
            });
        }
        const full = parts[0] + generated + parts[1];
        const newWindow = window.open('', '', 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=0, left=0');
        newWindow.document.body.innerHTML = full;
    }

    saveTemplate(template){
        const postData = Object.assign({}, template);
        for (const key in postData.model) {
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
