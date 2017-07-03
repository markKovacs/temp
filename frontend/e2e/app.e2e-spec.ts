import { OnlineApplicationFrontendPage } from './app.po';

describe('online-application-frontend App', () => {
  let page: OnlineApplicationFrontendPage;

  beforeEach(() => {
    page = new OnlineApplicationFrontendPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
