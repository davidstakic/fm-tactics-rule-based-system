export interface BackwardChainingRequest {
  targetGoal: string;
}

export interface BackwardChainingResponse {
  targetGoalTitle?: string;
  explanation?: string;
  requirements: BackwardRequirement[];
}

export interface BackwardRequirement {
  title?: string;
  description?: string;
}
