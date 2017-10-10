import {
    NavBarComponent,
    DashboardComponent,
    ApplicantComponent,
    SurveyGeneratorComponent,
    CalendarComponent,
    EditScreeningComponent,
    EvaluateScreeningsComponent,
    StartComponent,
    TemplateEditorComponent,
    EvaluateUserComponent
} from './components/index';
import {Routes, RouterModule} from '@angular/router';
import {AuthGuard} from './guards/index';
import {ApplicantsComponent} from './components/applicants/applicants.component';
import {FinalResultPageComponent} from "./components/final-result/final-result.component";
import {MarketingDataPageComponent} from "./components/marketing-data/marketing-data.component";
import {PersonalDataPageComponent} from "./components/personal-data/personal-data.component";

const appRoutes: Routes = [
    // routes - all for logged in user only
    {path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
    {path: 'applicants', component: ApplicantsComponent, canActivate: [AuthGuard]},
    {path: 'applicants/:id', component: ApplicantComponent, canActivate: [AuthGuard]},
    {path: 'calendar', component: CalendarComponent, canActivate: [AuthGuard]},
    {path: 'editscreening', component: EditScreeningComponent, canActivate: [AuthGuard]},
    {path: 'evaluate', component: EvaluateScreeningsComponent, canActivate: [AuthGuard]},
    {path: 'evaluateuser/:id', component: EvaluateUserComponent, canActivate: [AuthGuard]},
    {path: 'surveygenerator', component: SurveyGeneratorComponent, canActivate: [AuthGuard]},
    {path: 'edittemplate', component: TemplateEditorComponent, canActivate: [AuthGuard]},
    {path: 'final-result', component: FinalResultPageComponent, canActivate: [AuthGuard]},
    {path: 'marketing-data', component: MarketingDataPageComponent, canActivate: [AuthGuard]},
    {path: 'personal-data', component: PersonalDataPageComponent, canActivate: [AuthGuard]},

    // redirects to auth0 login
    {path: 'login', component: StartComponent},

    //
    {path: '**', redirectTo: 'dashboard'}
];

export const routing = RouterModule.forRoot(appRoutes);
