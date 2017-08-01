import {
    NavBarComponent,
    DashboardComponent,
    ApplicantComponent,
    ApplicantListComponent,
    SurveyGeneratorComponent,
    CalendarComponent,
    EditScreeningComponent,
    EvaluateScreeningsComponent,
    StartComponent,
    TemplateEditorComponent
} from './components/index';
import {Routes, RouterModule} from '@angular/router';
import {AuthGuard} from './guards/index';

const appRoutes: Routes = [
    // routes - all for logged in user only
    {path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
    {path: 'applicants', component: ApplicantListComponent, canActivate: [AuthGuard]},
    {path: 'applicants/:id', component: ApplicantComponent, canActivate: [AuthGuard]},
    {path: 'calendar', component: CalendarComponent, canActivate: [AuthGuard]},
    {path: 'editscreening', component: EditScreeningComponent, canActivate: [AuthGuard]},
    {path: 'evaluatescreenings', component: EvaluateScreeningsComponent},
    { path: 'surveygenerator', component: SurveyGeneratorComponent, canActivate: [AuthGuard]}, //
    { path: 'edittemplate', component: TemplateEditorComponent, canActivate: [AuthGuard]},

    // redirects to auth0 login
    {path: 'login', component: StartComponent},

    //
    {path: '**', redirectTo: 'dashboard'}
];

export const routing = RouterModule.forRoot(appRoutes);
