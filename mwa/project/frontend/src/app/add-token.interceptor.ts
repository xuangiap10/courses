import { Injectable, inject } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { StateService } from './state.service';

@Injectable()
export class AddTokenInterceptor implements HttpInterceptor {

  private state = inject(StateService);
  constructor() { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    const token = this.state.getToken();
    if (token) {
      const clone = request.clone({
        headers: request.headers.set('Authorization', 'Bearer ' + token)
      });
      return next.handle(clone);
    } else
      return next.handle(request);
  }
}
