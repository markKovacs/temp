import {User} from './user.model';
export class Applicant {

    name: string;
    adminId: number;
    location: string;
    status: string;
    attempts: number;
    blacklisted: boolean;
    processStartedAt: Date;
    email: string;
    user: User = new User();
    phoneNumber: string;

}
