export type MatchResult = 'WINNING' | 'DRAW' | 'LOSING';

export interface CEPMatchStateRequest {
  timestamp?: number;
  currentMinute: number;
  currentResult: MatchResult;
  ownTeamRedCards: number;
  opponentRedCards: number;
}

export interface CEPRecommendation {
  eventType?: string;
  eventDescription?: string;
  adjustedMentality?: string;
  adjustedPassing?: string;
  adjustedPressing?: string;
  adjustedDefensiveLineHeight?: string;
  adjustedTransition?: string;
  explanation?: string;
}
