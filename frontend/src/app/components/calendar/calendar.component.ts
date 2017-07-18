import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {GlobalEventsManager} from "../../global.eventsmanager";
import {HttpClient} from "../../_httpclient/httpclient";
import {Location, User} from "../../_models/index";

@Component({
    moduleId: module.id,
    templateUrl: 'calendar.component.html',
    styleUrls: ['calendar.component.css']
})
export class CalendarComponent {

    public users: any[] = [];
    public dates: Date[] = [];
    public loaded: boolean = false;
    public dateSelectorOn: boolean = false;
    public date: Date;
    public chosenDate: Date;
    public sourceList: any[] = [];
    public targetList: any[] = [];
    public isPostingGroupTimes: boolean = false;
    public isPostingIndividualTimes: boolean = false;
    public groupTimesSet: boolean = false;
    public individualTimesSet: boolean = false;

    constructor(
        private client: HttpClient,
        private router: Router,
        private eventsManager: GlobalEventsManager)
    {
        this.eventsManager.showNavBar(true);
        this.fetchUsers();
    }

    loadDates(){
        for (let user of this.users) {
            if (user.group != null && !this.dates.includes(user.group)) {
                this.dates.push(user.group)
            }
        }
        this.loaded = true;
    }

    formatDate(dateString){
      return dateString.toString().split("T")[0] + " - " + dateString.toString().split("T")[1]
    }

    openDateSelector(){
        this.dateSelectorOn = true;
        this.date = null;
    }

    addDate(){
        if (this.date && !this.dates.includes(this.date)){
            this.dates.push(this.date);
            this.dateSelectorOn = false;
        }
    }

    selectDate(date){
        this.chosenDate = date;
        this.groupTimesSet = null;
        this.sourceList = this.users.filter((user) => user.group == null);
        this.targetList = this.users.filter((user) => user.group == date);
    }

    showGroupTimes(){
        return this.chosenDate != null && !this.isPostingGroupTimes && !this.groupTimesSet;
    }

    showIndividualTimes(){
        return this.groupTimesSet && !this.isPostingIndividualTimes;
    }

    showLoading(){
       return this.isPostingGroupTimes || this.isPostingIndividualTimes;
    }

    setGroupTimes(){
        for (let user of this.targetList) {
            user.group = this.chosenDate;
        }
    }

    saveGroupTimes(){
        this.setGroupTimes();
        this.postGroupTimes();
    }

    saveIndividualTimes(){
        this.postIndividualTimes();
    }

    getIndividual(user){
      return user.individual ? user.individual : user.group
    }

    fetchUsers(){
      return setTimeout(()=>{
          this.users = [
            {id: 1, name: "Kovács Béla", age: 18, group: null, individual: null},
            {id: 2, name: "Varga Lajos", age: 29, group: null, individual: null},
            {id: 3, name: "Nagy Kázmér", age: 32, group: "2017-08-20T15:00", individual: "2017-08-20T20:00"},
            {id: 4, name: "Kis Ottokár", age: 25, group: null, individual: null},
            {id: 5, name: "Kolompár Shakira", age: 23, group: null, individual: null},
            {id: 6, name: "Magyar Oszkár", age: 42, group: "2017-08-20T12:00", individual: "2017-08-20T20:00"},
            {id: 7, name: "Horváth Miklós", age: 57, group: "2017-08-20T15:00", individual: "2017-08-20T21:00"}
          ];
          this.loadDates();
      }, 1000);
    }

    postGroupTimes(){
        let sendData = [];
        for (let user of this.targetList) { sendData.push({id: user.id, group: user.group}) }
        console.log("should post this:", {groupTimes: sendData});
        this.isPostingGroupTimes = true;
        return setTimeout(() => {
            this.isPostingGroupTimes = false;
            this.groupTimesSet = true;
        }, 1000)
    }

    postIndividualTimes(){
        let sendData = [];
        for (let user of this.targetList) { sendData.push({id: user.id, individual: user.individual}) }
        console.log("should post this:", {groupTimes: sendData});
        this.isPostingIndividualTimes = true;
        this.individualTimesSet = false;
        return setTimeout(() => {
            this.isPostingIndividualTimes = false;
            this.individualTimesSet = true;
            this.chosenDate = null;
            this.groupTimesSet = false;
        }, 1000)
    }
}
