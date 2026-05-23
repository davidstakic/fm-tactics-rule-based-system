import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CEPMatchStateRequest, CEPRecommendation } from '../models/cep.model';

@Injectable({ providedIn: 'root' })
export class CEPApiService {
  private readonly apiUrl = 'http://localhost:8080/api/cep';

  constructor(private readonly http: HttpClient) {}

  startMatch(): Observable<CEPRecommendation[]> {
    return this.http.post<CEPRecommendation[]>(`${this.apiUrl}/match/start`, null);
  }

  processMatchState(request: CEPMatchStateRequest): Observable<CEPRecommendation[]> {
    return this.http.post<CEPRecommendation[]>(`${this.apiUrl}/match/state`, request);
  }
}
