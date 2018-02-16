export class Email {
    fromAddress: string;
    toAddress: string;
    subject: string;
    html: string;
    application: string;
    success: boolean;
    sent: Date;
    deliveredAt: Date;
    opened: boolean;
    openedAt: Date;
}