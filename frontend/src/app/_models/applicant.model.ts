import {User} from './user.model';
import {UsersScreeningStep} from "./screening/users-screening-step.model";
export class Applicant {

    name: string;
    id: number;
    location: string;
    status: string;
    attempts: number;
    blacklisted: boolean;
    processStartedAt: Date;
    email: string;
    user: User = new User();
    phoneNumber: string;

    finalResult: boolean;
    finalResultSent:boolean;

    // locally used
    send = false;
    details: UsersScreeningStep;
    hasPersonalData = false;

}
