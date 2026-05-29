export type PhysicalProfile = 'FAST' | 'STRONG' | 'AVERAGE';
export type MidfieldQuality = 'CREATIVE' | 'AGGRESSIVE' | 'BALANCED';
export type AttackType = 'WING_PLAY' | 'CENTRAL_PLAY' | 'PRESSING_ATTACKERS';
export type PlayingStyle = 'POSSESSION_BASED' | 'DIRECT' | 'COUNTER_ATTACK';
export type DefenseLineEngagement = 'HIGH_PRESS' | 'MID_BLOCK' | 'LOW_BLOCK';
export type OpponentWeakness =
  | 'VULNERABLE_ON_FLANKS'
  | 'WEAK_AERIAL_DEFENSE'
  | 'SLOW_DEFENDERS'
  | 'UNRELIABLE_GOALKEEPER'
  | 'NO_OBVIOUS_WEAKNESS';
export type CompetitionType = 'FRIENDLY' | 'LEAGUE' | 'CUP' | 'CONTINENTAL';
export type MatchImportance = 'LOW' | 'MEDIUM' | 'HIGH';
export type LocationType = 'HOME' | 'AWAY' | 'NEUTRAL';

export interface ForwardChainingRequest {
  teamProfile: {
    teamStrength: number;
    formLast5Matches: string;
    tacticalFitness: number;
    physicalProfile: PhysicalProfile;
    midfieldQuality: MidfieldQuality;
    highLineCapability: boolean;
    attackType: AttackType;
  };
  opponentProfile: {
    opponentStrength: number;
    playingStyle: PlayingStyle;
    lineEngagement: DefenseLineEngagement;
    weakness: OpponentWeakness;
  };
  matchContext: {
    competitionType: CompetitionType;
    importance: MatchImportance;
    location: LocationType;
  };
}

export interface TacticalRecommendation {
  basicSettings?: {
    recommendedFormation?: string;
    mentality?: string;
  };
  teamInstructions?: {
    passingDirectness?: string;
    pressingIntensity?: string;
    defensiveLineHeight?: string;
    transition?: string;
  };
  explanationSteps: TacticalExplanationStep[];
  advantages: TacticalNote[];
  risks: TacticalNote[];
}

export interface TacticalExplanationStep {
  decision?: string;
  selectedValue?: string;
  explanation?: string;
}

export interface TacticalNote {
  title?: string;
  description?: string;
}
