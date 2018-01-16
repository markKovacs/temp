import {Component, OnChanges, SimpleChanges} from '@angular/core';
import {HttpClient} from '../../_httpclient/httpclient';
import {Location, Template, PostResponse} from '../../_models/index';
import Mustache from 'mustache/mustache';
import * as _ from 'lodash';
import {Message} from 'primeng/primeng';
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
    public selectedTemplate: Template;
    public messages: Message[] = [];
    editingName = false;
    public options: Object = {
        immediateAngularModelUpdate: true
    };

    constructor(private client: HttpClient,
                private emailTemplateService: EmailTemplateService) {
        const locId = JSON.parse(localStorage.getItem('chosenLocation')).id;
        this.client.get('/api/locations/' + locId).subscribe(
            data => {
                this.location = data;
                this.getTemplates();
            }
        );

    }

    getTemplates(): void {
        this.emailTemplateService.getTemplates(this.location.id)
            .subscribe(
                (templates: Template[]) => {
                    this.templates = templates;
                    for (const template of this.templates) {
                        template.model = (template.model == null) ? {} : JSON.parse(template.model);
                    }
                    this.masterTemplate = this.templates.filter(element => element.master)[0];
                    this.templates.sort((a, b) =>  {
                        return b.templateOrder - a.templateOrder
                    })
                });
    }

    saveTemplate(template): void {
        const copy = Object.assign({}, template);
        copy.model = JSON.stringify(copy.model);
        this.emailTemplateService.saveTemplate(copy)
            .subscribe(
                (response: PostResponse) => {
                    if (response.success) {
                        alert('template saved');
                        // todo show msg growl with success severity
                    } else {
                        alert('error while saving');
                        // todo show msg growl with error severity
                    }
                }
            );
    }

    saveLocation(){
        this.client.post('/api/locations', this.location).subscribe(
            data => alert("Location data saved")
        )
    }

    selectTemplate(template) {
        this.selectedTemplate = template;
    }

    getKeys(model: any): any[] {
        return _.keys(model)
    }

    keyupHandlerFunction(event: string): void {
        this.selectedTemplate.template = event;
    }

    openPreviewWindow(template) {
        const generated = Mustache.render(template, this.selectedTemplate.model);
        const generatedMaster = Mustache.render(this.masterTemplate.template, this.masterTemplate.model);
        const commonTemplateParts = generatedMaster.split('({[content]})');

        if (commonTemplateParts.length !== 2) {
            // todo show msg growl with error severity
        }

        const full = commonTemplateParts[0] + generated + commonTemplateParts[1];
        const newWindow = window.open(
            '',
            '',
            'toolbar=no,' +
            'location=no,' +
            'directories=no,' +
            'status=no,' +
            'menubar=no,' +
            'scrollbars=yes,' +
            'resizable=yes,' +
            'width=800,' +
            'height=600,' +
            'top=70,' +
            'left=' + ((window.screen.width / 2) - 400));
        newWindow.document.body.innerHTML = full;
    }

}
