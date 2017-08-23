import {
    Component,
    OnDestroy,
    AfterViewInit,
    EventEmitter,
    Input,
    Output, ElementRef
} from '@angular/core';

@Component({
    selector: 'app-tinymce',
    template: `<textarea id="{{elementId}}">{{initialHtml}}</textarea>`
})
export class TinyMceComponent implements AfterViewInit, OnDestroy {
    @Input() elementId: String;
    @Input() initialHtml: String;
    @Output() onEditorKeyup = new EventEmitter<any>();

    editor;
    
    ngAfterViewInit() {
        tinymce.init({
            selector: '#' + this.elementId,
            plugins: ['link', 'paste', 'table'],
            skin_url: 'assets/skins/lightgray',
            setup: editor => {
                this.editor = editor;
                editor.on('keyup', () => {
                    const content = editor.getContent();
                    this.onEditorKeyup.emit(content);
                });
            },
        });
    }

    ngOnDestroy() {
        tinymce.remove(this.editor);
    }
}
