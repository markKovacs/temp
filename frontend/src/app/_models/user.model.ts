import {Application} from "./application";
import {Email} from "./email.model";

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

    courseId: string;

    emails: Email[] = [];

}
