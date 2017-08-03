import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {routing} from './app.routing';
import {
  DataTableModule,
  InputTextareaModule,
  PanelModule,
  DropdownModule,
  PickListModule,
  CalendarModule,
  GrowlModule,
  Message
} from 'primeng/primeng';
import {SharedModule} from 'primeng/primeng';
import {DomSanitizer} from '@angular/platform-browser';
import { DatePipe } from '@angular/common';
import {GlobalEventsManager} from './global.eventsmanager';
import {HttpClient} from './_httpclient/httpclient';
import {AppComponent} from './app.component';
import {
    LocationTestService,
    QuestionService

} from './_services/index';
import { FilterEnabled } from './_pipes/index'
import {
    NavBarComponent,
    DashboardComponent,
    DashboardMotivationComponent,
    DashboardScreeningComponent,
    ApplicantComponent,
    ApplicantVideoComponent,
    ApplicantTestComponent,
    ApplicantListComponent,
    SurveyGeneratorComponent,
    SurveyEditorComponent,
    OptionEditorComponent,
    QuestionEditorComponent,
    CalendarComponent,
    StartComponent,
    EvaluateScreeningsComponent,
    EditScreeningComponent,
    TemplateEditorComponent,
    SurveyBaseDataComponent
} from './components/index';
import {AuthGuard} from './guards/index';
import {ScreeningService} from "./_services/screening.service";
import {DateFormatPipe, MomentModule} from "angular2-moment";
import { FroalaEditorModule, FroalaViewModule } from 'angular-froala-wysiwyg';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import {ApplicantScreeningComponent} from "./components/applicant-screening/applicant-screening.component";

@NgModule({
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        HttpModule,
        routing,
        DataTableModule,
        InputTextareaModule,
        PanelModule,
        DropdownModule,
        SharedModule,
        PickListModule,
        CalendarModule,
        MomentModule,
        GrowlModule,
        FroalaEditorModule.forRoot(), FroalaViewModule.forRoot(),
        CarouselModule.forRoot(),
        BsDropdownModule.forRoot()
    ],
    declarations: [
        AppComponent,
        NavBarComponent,
        DashboardComponent,
        DashboardMotivationComponent,
        DashboardScreeningComponent,
        ApplicantComponent,
        ApplicantTestComponent,
        ApplicantVideoComponent,
        ApplicantListComponent,
        SurveyGeneratorComponent,
        SurveyEditorComponent,
        QuestionEditorComponent,
        OptionEditorComponent,
        CalendarComponent,
        EditScreeningComponent,
        EvaluateScreeningsComponent,
        StartComponent,
        TemplateEditorComponent,
        FilterEnabled,
        SurveyBaseDataComponent,
        ApplicantScreeningComponent
    ],
    providers: [
        AuthGuard,
        GlobalEventsManager,
        HttpClient,
        LocationTestService,
        QuestionService,
        DateFormatPipe,
        DatePipe,
        ScreeningService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    bootstrap: [AppComponent]
})
export class AppModule {
}
