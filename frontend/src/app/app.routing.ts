import {
  NavBarComponent,
  DashboardComponent,
  ApplicantComponent
} from './components/index';
import { Routes, RouterModule } from '@angular/router';
import {AuthGuard} from './guards/index';

const appRoutes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'applicants/:id', component: ApplicantComponent },

  { path: '**', redirectTo: 'dashboard' }
];

export const routing = RouterModule.forRoot(appRoutes);
