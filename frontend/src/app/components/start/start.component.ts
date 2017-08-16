import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import {Location} from '@angular/common';


@Component({
    moduleId: module.id,
    templateUrl: 'start.component.html',
    styleUrls: ['start.component.css']
})
export class StartComponent implements OnInit {

    constructor(private router: Router,
                private location: Location,
                private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {
        this.activatedRoute.queryParams.subscribe((params: Params) => {

            // if JWT is supplied save it and delete from the history.
            if (params['jwt']) {
                console.log("jwt token received");
                localStorage.setItem('adminAuthToken', params['jwt']) ;
                this.location.replaceState('login');
            }

            // if no auth token found - redirect to login.
            if (!localStorage.getItem('adminAuthToken')) {
                console.log("NO jwt token received");
                window.location.href = 'http://35.158.247.216:9002/api/login';
            } else {
                this.router.navigate(['dashboard']);
            }
        })
    }
}
