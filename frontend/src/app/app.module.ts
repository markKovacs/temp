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
  DragDropModule,
    DialogModule
} from 'primeng/primeng';
import {SharedModule} from 'primeng/primeng';
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
    ApplicantsComponent,
    SurveyGeneratorComponent,
    SurveyEditorComponent,
    OptionEditorComponent,
    QuestionEditorComponent,
    CalendarComponent,
    StartComponent,
    EvaluateScreeningsComponent,
    EditScreeningComponent,
    TemplateEditorComponent,
    SurveyBaseDataComponent,
    ApplicantsTableComponent,
    FinalResultPageComponent,
    TinyMceComponent
} from './components/index';
import {AuthGuard} from './guards/index';
import {ScreeningService} from "./_services/screening.service";

import { CarouselModule } from 'ngx-bootstrap/carousel';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import {ApplicantScreeningComponent} from "./components/applicant-screening/applicant-screening.component";
import {EditStepsBoxComponent} from "./components/evaluateScreenings/edit-steps-box/edit-steps-box.component";
import {EvaluateStepsComponent} from "./components/evaluateScreenings/evaluate-steps/evaluate-steps.component";
import {EvaluateUserComponent} from "./components/evaluateScreenings/evaluate-user/evaluate-user.component";
import {AlertService} from "./_services/alert.service";
import {AlertComponent} from "./components/alert/alert.component";
import {ApplicantService} from './_services/applicants.service';
import {UserService} from './_services/user.service';
import {EmailTemplateService} from './_services/email-template.service';
import {ListboxModule} from 'primeng/primeng';
import {InputSwitchModule} from 'primeng/primeng';
import {MarketingDataPageComponent} from "./components/marketing-data/marketing-data.component";
import {PersonalDataPageComponent} from "./components/personal-data/personal-data.component";
import { RubricFormComponent } from './components/rubric-form/rubric-form.component';
import {RubricService} from "./_services/rubric.service";
import {TestResultService} from "./_services/testResult.service";
import {StatDataService} from "./_services/statdata.service";
import {KeysPipe} from "./_pipes/map-to-iterable.pipe";



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

        GrowlModule,
        DragDropModule,
        CarouselModule.forRoot(),
        BsDropdownModule.forRoot(),
        ListboxModule,
        InputSwitchModule,
        DialogModule
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
        ApplicantsComponent,
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
        ApplicantScreeningComponent,
        EditStepsBoxComponent,
        EvaluateStepsComponent,
        EvaluateUserComponent,
        AlertComponent,
        ApplicantsTableComponent,
        TinyMceComponent,
        FinalResultPageComponent,
        MarketingDataPageComponent,
        PersonalDataPageComponent,
        RubricFormComponent,
        KeysPipe
    ],
    providers: [
        AuthGuard,
        GlobalEventsManager,
        HttpClient,
        LocationTestService,
        QuestionService,

        DatePipe,
        ScreeningService,
        AlertService,
        ApplicantService,
        UserService,
        EmailTemplateService,
        RubricService,
        TestResultService,

        StatDataService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    bootstrap: [AppComponent]
})
export class AppModule {
}
