export class UserScreeningDisplayModel {
    stepName: string;
    interviewer: string;
    points: number;
    comment: string;
    status: string;
    criterias: UserCriteriaDisplayModel[];
}

export class UserCriteriaDisplayModel {
    criteriaName: string;
    points: number;
    comment: string;
    status: string;
}