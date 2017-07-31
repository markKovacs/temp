import {Component} from '@angular/core';
import {HttpClient} from "../../_httpclient/httpclient";
import {GlobalEventsManager} from "../../global.eventsmanager";
import {Location, Template, PostResponse} from "../../_models/index";
import {Message} from 'primeng/primeng';
import Mustache from 'mustache/mustache';

@Component({
    moduleId: module.id,
    templateUrl: 'template-editor.component.html',
    styleUrls: ['template-editor.component.css']
})
export class TemplateEditorComponent {

    public location: Location;
    public templates: Template[];
    public chosenTemplate: Template;
    public messages: Message[] = [];

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
            (templates: Template[]) => this.templates = templates
        )
    }

    chooseTemplate(template){
        this.chosenTemplate = template;
    }

    previewTemplate(template){
      let generated = Mustache.render(template, {name: "bela"});
      let newWindow = window.open("", "Title", "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=600, top=0, left=0");
      newWindow.document.body.innerHTML = generated;
    }

    saveTemplate(template){
        this.client.post('/api/templates/save', template).subscribe(
            (response: PostResponse) => this.messages.push(
                {
                    severity: 'success',
                    summary: 'Save completed',
                    detail: this.chosenTemplate.name
                })
        )
    }

}
