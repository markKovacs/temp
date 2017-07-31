import {Component, Input} from "@angular/core";
import {UserScreening} from "../../_models/user-screening.model";
import {Router} from "@angular/router";

@Component({
    moduleId: module.id,
    templateUrl: 'dashboard-screening.component.html',
    styleUrls: ['dashboard-screening.component.css'],
    selector: 'dashboard-screening'
})
export class DashboardScreeningComponent {

    @Input() public userWithScreening: UserScreening;

    constructor(private router: Router) {}

    getApplicant(id) {
        this.router.navigate(['applicants/' + id]);
    }
}