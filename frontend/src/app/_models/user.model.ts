import {Application} from "./application";

export class User {

    id: number;
    givenName: string;
    familyName: string;
    dateOfBirth: number;
    phoneNumber: string;
    email: string;

    timesApplied: number;
    location: string;

    applications: Application[] = [];

}
