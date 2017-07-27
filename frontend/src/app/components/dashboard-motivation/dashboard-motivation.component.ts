
import {Component, Input} from "@angular/core";
import {UserMotivation} from "../../_models/user-motivation.model";
import {Router} from "@angular/router";

@Component({
    moduleId: module.id,
    templateUrl: 'dashboard-motivation.component.html',
    styleUrls: ['dashboard-motivation.component.css'],
    selector: 'dashboard-motivation'
})
export class DashboardMotivationComponent {

    @Input() public userWithVideo: UserMotivation;

    constructor(private router: Router) {}

    getApplicant(id){
        console.log(id);
        this.router.navigate(['applicants/' + id])
    }
}