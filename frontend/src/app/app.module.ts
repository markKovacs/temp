import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {routing} from './app.routing';
import {DataTableModule, InputTextareaModule, PanelModule, DropdownModule} from 'primeng/primeng';
import {SharedModule} from 'primeng/primeng';
import {DomSanitizer} from '@angular/platform-browser';
import {GlobalEventsManager} from './global.eventsmanager';
import {HttpClient} from './_httpclient/httpclient';
import {AppComponent} from './app.component';
import {
    LocationTestService
} from './_services/index';
import {
    NavBarComponent,
    DashboardComponent,
    ApplicantComponent,
    ApplicantListComponent,
    StartComponent,
    SurveyGeneratorComponent
} from './components/index';
import {AuthGuard} from './guards/index';

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
        SharedModule
    ],
    declarations: [
        AppComponent,
        NavBarComponent,
        DashboardComponent,
        ApplicantComponent,
        ApplicantListComponent,
        StartComponent,
        SurveyGeneratorComponent

    ],
    providers: [
        AuthGuard,
        GlobalEventsManager,
        HttpClient,
        LocationTestService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    bootstrap: [AppComponent]
})
export class AppModule {
}
