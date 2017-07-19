import {
    NavBarComponent,
    DashboardComponent,
    ApplicantComponent,
    ApplicantListComponent,
    CalendarComponent,
    StartComponent
} from './components/index';
import {Routes, RouterModule} from '@angular/router';
import {AuthGuard} from './guards/index';

const appRoutes: Routes = [
    // routes - all for logged in user only
    {path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
    {path: 'applicants', component: ApplicantListComponent, canActivate: [AuthGuard]},
    {path: 'applicants/:id', component: ApplicantComponent, canActivate: [AuthGuard]},
    {path: 'calendar', component: CalendarComponent, canActivate: [AuthGuard]},

    // redirects to auth0 login
    {path: 'login', component: StartComponent},

    //
    {path: '**', redirectTo: 'dashboard'}
];

export const routing = RouterModule.forRoot(appRoutes);
