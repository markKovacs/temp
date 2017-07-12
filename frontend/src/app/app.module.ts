import {BrowserModule} from '@angular/platform-browser';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {routing} from './app.routing';
// import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';
import {GlobalEventsManager} from './global.eventsmanager';
import {HttpClient} from './_httpclient/httpclient';
import {AppComponent} from './app.component';
import {
  NavBarComponent,
  DashboardComponent,
  ApplicantComponent,
  ApplicantListComponent
} from './components/index';
import { AuthGuard } from './guards/index';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        routing,
        // DomSanitizer,
        // SafeUrl,
        // SafeResourceUrl
    ],
    declarations: [
        AppComponent,
        NavBarComponent,
        DashboardComponent,
        ApplicantComponent,
        ApplicantListComponent
    ],
    providers: [
        AuthGuard,
        GlobalEventsManager,
        HttpClient
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    bootstrap: [AppComponent]
})
export class AppModule { }
