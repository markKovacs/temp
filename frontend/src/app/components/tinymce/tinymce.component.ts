import {
    Component,
    OnDestroy,
    AfterViewInit,
    EventEmitter,
    Input,
    Output, ElementRef, OnChanges, SimpleChanges
} from '@angular/core';

@Component({
    selector: 'app-tinymce',
    template: '<div style="display: block; width: 100%;"><textarea id="{{elementId}}"></textarea></div>'
})
export class TinyMceComponent implements AfterViewInit, OnDestroy, OnChanges {
    @Input() elementId: string;
    @Input() initialHtml: string;
    @Output() onEditorKeyup = new EventEmitter<string>();
    editor;

    ngAfterViewInit() {
        this.populateEditor();
    }

    populateEditor(): void {
        tinymce.init({
            selector: '#' + this.elementId,
            plugins: ['link', 'paste', 'table'],
            width : '75%',
            skin_url: 'assets/skins/lightgray',
            setup: editor => {
                this.editor = editor;
                editor.on('init', () => {
                    editor.setContent(this.initialHtml);
                });
                editor.on('keyup', () => {
                    const content = editor.getContent();
                    this.onEditorKeyup.emit(content);
                });
            },
        });
    }

    ngOnChanges(changes: SimpleChanges): void {
        tinymce.remove(this.editor);
        this.populateEditor();
    }

    ngOnDestroy() {
        tinymce.remove(this.editor);
    }
}
