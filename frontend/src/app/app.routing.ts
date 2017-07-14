import {
  NavBarComponent,
  DashboardComponent,
  ApplicantComponent,
  ApplicantListComponent,
  StartComponent
} from './components/index';
import { Routes, RouterModule } from '@angular/router';
import {AuthGuard} from './guards/index';

const appRoutes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'applicants', component: ApplicantListComponent, canActivate: [AuthGuard] },
  { path: 'applicants/:id', component: ApplicantComponent, canActivate: [AuthGuard] },
  { path: 'login', component: StartComponent },

  { path: '**', redirectTo: 'dashboard' }
];

export const routing = RouterModule.forRoot(appRoutes);
