import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  BackwardChainingRequest,
  BackwardChainingResponse,
} from '../models/backward-chaining.model';

@Injectable({ providedIn: 'root' })
export class BackwardChainingApiService {
  private readonly apiUrl = 'http://localhost:8080/api/backward-chaining/requirements';

  constructor(private readonly http: HttpClient) {}

  requirements(request: BackwardChainingRequest): Observable<BackwardChainingResponse> {
    return this.http.post<BackwardChainingResponse>(this.apiUrl, request);
  }
}
