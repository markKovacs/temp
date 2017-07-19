export class Option {

    id: string;
    optionContent: string;

    constructor(){
        if (!this.id){
            this.id = Math.random().toString(36).substr(2, 9);
        }
    }

}
