package com.ftn.sbnz.model.forward.factory;

import com.ftn.sbnz.model.enums.DefensiveLineHeight;
import com.ftn.sbnz.model.enums.Formation;
import com.ftn.sbnz.model.enums.Mentality;
import com.ftn.sbnz.model.enums.PassingDirectness;
import com.ftn.sbnz.model.enums.PressingIntensity;
import com.ftn.sbnz.model.enums.TransitionAfterLossOfBall;
import com.ftn.sbnz.model.forward.TacticalExplanationStep;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public final class TacticalExplanationFactory {
    private TacticalExplanationFactory() {
    }

    public static TacticalExplanationStep formation(Formation formation, Map<String, Integer> scoreBreakdown) {
        String reasons = formationReasons(scoreBreakdown);
        TacticalExplanationStep step = new TacticalExplanationStep(
                "Formation",
                display(formation),
                "The formation was selected because " + reasons + ".");
        addFormationTradeoffs(step, formation);
        return step;
    }

    public static TacticalExplanationStep mentality(Mentality mentality, String reason) {
        TacticalExplanationStep step = new TacticalExplanationStep(
                "Mentality",
                display(mentality),
                "The mentality was chosen because " + reason + ".");

        switch (mentality) {
            case VERY_ATTACKING:
            case ATTACKING:
                step.addAdvantage("More pressure on the opponent", "The team commits more players forward and tries to create chances earlier.");
                step.addRisk("Space behind the team", "An aggressive approach can leave more room for counters if possession is lost.");
                break;
            case POSITIVE:
                step.addAdvantage("Front-foot control", "The team can attack proactively without becoming too open.");
                step.addRisk("Needs good execution", "If passing or pressing is loose, the team may be stretched between attack and defence.");
                break;
            case DEFENSIVE:
            case VERY_DEFENSIVE:
                step.addAdvantage("Stronger protection", "The team keeps a more secure shape and reduces the opponent's space.");
                step.addRisk("Less attacking presence", "The team may struggle to keep the ball and create enough chances.");
                break;
            case CAUTIOUS:
                step.addAdvantage("Controlled risk", "The team can stay compact while still looking for useful attacking moments.");
                step.addRisk("Can invite pressure", "If the team drops too deep, the opponent may control the tempo.");
                break;
            default:
                step.addAdvantage("Stable baseline", "The team keeps a balanced approach that can adapt during the match.");
                step.addRisk("No strong specialization", "The setup may need in-match adjustments if one side clearly dominates.");
                break;
        }
        return step;
    }

    public static TacticalExplanationStep passing(PassingDirectness passing, String reason) {
        TacticalExplanationStep step = new TacticalExplanationStep(
                "Passing",
                display(passing),
                "Passing directness was chosen because " + reason + ".");

        switch (passing) {
            case DIRECT:
                step.addAdvantage("Faster progression", "The team can reach dangerous areas quickly and attack space before it closes.");
                step.addRisk("More turnovers", "Longer passes are harder to control and can give possession back to the opponent.");
                break;
            case SHORTER:
                step.addAdvantage("Better possession control", "Shorter passing helps the team keep the ball and combine through midfield.");
                step.addRisk("Slower attacks", "The team may need patience to break compact opponents down.");
                break;
            default:
                step.addAdvantage("Balanced ball movement", "The team keeps flexibility between safe circulation and faster forward passes.");
                step.addRisk("May lack a clear rhythm", "The passing approach may need tuning if the opponent forces a specific game state.");
                break;
        }
        return step;
    }

    public static TacticalExplanationStep pressing(PressingIntensity pressing, String reason) {
        TacticalExplanationStep step = new TacticalExplanationStep(
                "Pressing",
                display(pressing),
                "Pressing intensity was chosen because " + reason + ".");

        switch (pressing) {
            case HIGH:
                step.addAdvantage("Earlier ball recovery", "The team can disrupt build-up and win possession closer to goal.");
                step.addRisk("Fitness and space risk", "High pressing can tire players and leave space if the press is bypassed.");
                break;
            case LOW:
                step.addAdvantage("Compact defensive block", "The team protects space and reduces the risk of being played through.");
                step.addRisk("Less pressure on the ball", "The opponent may have more time to build attacks.");
                break;
            default:
                step.addAdvantage("Sustainable pressure", "The team can contest possession without overcommitting too often.");
                step.addRisk("Timing matters", "If players press at different moments, the opponent can play through gaps.");
                break;
        }
        return step;
    }

    public static TacticalExplanationStep defensiveLine(DefensiveLineHeight defensiveLine, String reason) {
        TacticalExplanationStep step = new TacticalExplanationStep(
                "Defensive line",
                display(defensiveLine),
                "Defensive line height was chosen because " + reason + ".");

        switch (defensiveLine) {
            case HIGH:
                step.addAdvantage("Keeps the team compact higher up", "A higher line supports pressure and keeps attackers closer to goal.");
                step.addRisk("Space in behind", "Fast opponents can attack the space behind the defence.");
                break;
            case LOW:
                step.addAdvantage("Protects dangerous space", "A lower line reduces balls in behind and helps the defence stay compact.");
                step.addRisk("Can concede territory", "The opponent may spend more time around the box.");
                break;
            default:
                step.addAdvantage("Balanced defensive spacing", "The team avoids being too deep or too exposed.");
                step.addRisk("Needs coordination", "The line still has to move together to avoid gaps between units.");
                break;
        }
        return step;
    }

    public static TacticalExplanationStep transition(TransitionAfterLossOfBall transition, String reason) {
        TacticalExplanationStep step = new TacticalExplanationStep(
                "Transition after losing the ball",
                display(transition),
                "The transition instruction was chosen because " + reason + ".");

        switch (transition) {
            case COUNTER_PRESS:
                step.addAdvantage("Immediate pressure", "The team tries to win the ball back before the opponent can organize.");
                step.addRisk("Open if beaten", "If the first pressure fails, the opponent can attack exposed space.");
                break;
            case HOLD_SHAPE:
                step.addAdvantage("Keeps structure", "The team avoids unnecessary chasing and protects its defensive shape.");
                step.addRisk("Slower ball recovery", "The opponent may have more time to start an attack.");
                break;
            default:
                step.addAdvantage("Safer reset", "The team gets players behind the ball and reduces transition danger.");
                step.addRisk("Less attacking momentum", "Dropping back can make it harder to sustain pressure after losing possession.");
                break;
        }
        return step;
    }

    private static String formationReasons(Map<String, Integer> scoreBreakdown) {
        if (scoreBreakdown == null || scoreBreakdown.isEmpty()) {
            return "it is the best available fit for the team and match context";
        }

        String reasons = scoreBreakdown.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue() > 0)
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));

        if (reasons.isEmpty()) {
            return "it is the least risky available fit for the team and match context";
        }
        return reasons;
    }

    private static void addFormationTradeoffs(TacticalExplanationStep step, Formation formation) {
        switch (formation) {
            case FORMATION_442:
                step.addAdvantage("Simple and reliable structure", "The team gets clear partnerships in attack, midfield, and defence, which makes the shape easy to execute.");
                step.addAdvantage("Good wide and box presence", "The formation can support wing play while still keeping two forwards available for crosses and second balls.");
                step.addRisk("Can lose central control", "Against teams with an extra midfielder, the central area can become difficult to control.");
                step.addRisk("Can become predictable", "If wide attacks are blocked, the team may need another route to create chances.");
                break;
            case FORMATION_433:
                step.addAdvantage("Balanced attacking structure", "The team has width, midfield support, and enough players high up the pitch to press or attack quickly.");
                step.addAdvantage("Good fit for proactive football", "The shape lets the team keep pressure on the opponent without completely sacrificing midfield balance.");
                step.addRisk("Space behind full-backs", "If both full-backs push high, the opponent can counter into the wide spaces behind them.");
                step.addRisk("Depends on midfield work rate", "The midfield three must cover a lot of ground to keep the team connected.");
                break;
            case FORMATION_4231:
                step.addAdvantage("Strong central support", "The attacking midfielder can connect midfield and attack while the double pivot protects the defence.");
                step.addAdvantage("Good control between the lines", "Creative players get useful positions to receive the ball and create chances.");
                step.addRisk("Striker can become isolated", "If the attacking midfielders do not support quickly, the lone forward may struggle to hold the ball.");
                step.addRisk("Requires disciplined wide players", "The wide players must help defensively or the team can be exposed on the flanks.");
                break;
            case FORMATION_4141:
                step.addAdvantage("Strong midfield coverage", "The team gets good protection in front of the defence while still keeping passing options in midfield.");
                step.addAdvantage("Useful for controlled possession", "The shape can circulate the ball patiently and reduce unnecessary risk.");
                step.addRisk("Limited penalty-box presence", "With only one striker, the team may need midfield runners to create enough threat in the box.");
                step.addRisk("Can become too passive", "If the midfield line drops too deep, the team may struggle to progress the ball.");
                break;
            case FORMATION_4312:
                step.addAdvantage("Strong central overload", "The narrow midfield and attacking midfielder help the team combine through central areas.");
                step.addAdvantage("Two forwards stay connected", "The front two can combine, press centre-backs, and attack second balls together.");
                step.addRisk("Limited natural width", "The formation depends on full-backs for width, which can leave wide spaces in transition.");
                step.addRisk("Can struggle against wide attacks", "Opponents with strong wingers can stretch the shape and force the midfield to shift constantly.");
                break;
            case FORMATION_451:
                step.addAdvantage("Compact midfield block", "The team can protect central areas and make it harder for the opponent to play through midfield.");
                step.addAdvantage("Useful for controlled defending", "The shape gives wide midfielders and central midfielders clear defensive responsibilities.");
                step.addRisk("Lone striker can lack support", "The team may struggle to keep possession high up the pitch if midfield runners arrive late.");
                step.addRisk("Can invite pressure", "If the team sits too deep, the opponent may control territory for long periods.");
                break;
            case FORMATION_4123:
                step.addAdvantage("Good attacking width", "The front three can stretch the opponent while the defensive midfielder protects central space.");
                step.addAdvantage("Supports creative possession", "The midfield can build attacks while still leaving several forward options ahead of the ball.");
                step.addRisk("Requires strong defensive midfielder", "If the holding midfielder is bypassed, the defence can become exposed.");
                step.addRisk("Can be demanding tactically", "The shape needs coordinated movement between midfielders and forwards to avoid becoming disconnected.");
                break;
            case FORMATION_532:
                step.addAdvantage("Strong defensive security", "The back five and three central midfielders make the team difficult to break down.");
                step.addAdvantage("Good counter-attacking base", "The two forwards can stay ready for transitions while the team remains protected behind them.");
                step.addRisk("Can lack width in attack", "If wing-backs are pinned back, the team may struggle to progress down the flanks.");
                step.addRisk("May concede possession", "The shape can become deep and allow the opponent to control the ball.");
                break;
            case FORMATION_523:
                step.addAdvantage("Secure base with attacking outlets", "The back five protects the team while the front three gives clear options for counters.");
                step.addAdvantage("Useful for wide transitions", "The shape can attack quickly into wide areas when space appears.");
                step.addRisk("Midfield can be outnumbered", "With only two central midfielders, the team may struggle to control the middle.");
                step.addRisk("Needs disciplined wing-backs", "If wing-backs mistime their forward runs, the team can be exposed on the sides.");
                break;
            case FORMATION_541:
                step.addAdvantage("Very compact defensive shape", "The team protects the box and wide areas with a five-player defensive line.");
                step.addAdvantage("Good for protecting difficult match states", "The shape helps reduce space and slow the opponent's attacks.");
                step.addRisk("Limited attacking threat", "The lone striker can become isolated and the team may struggle to create chances.");
                step.addRisk("Can invite sustained pressure", "A very defensive shape may keep the team too close to its own goal.");
                break;
            case FORMATION_343:
                step.addAdvantage("Aggressive attacking width", "The front three and wide players can stretch the opponent and keep pressure high.");
                step.addAdvantage("Strong forward presence", "The team can commit several players into attacking areas quickly.");
                step.addRisk("Wide defensive spaces", "If wide players are caught high, the back three can be pulled into uncomfortable wide areas.");
                step.addRisk("Requires high physical output", "The wide players must cover long distances in both directions.");
                break;
            case FORMATION_352:
                step.addAdvantage("Central midfield strength", "The team gets three central midfielders and two forwards, which helps both control and attacking presence.");
                step.addAdvantage("Good for wing-back attacks", "Wing-backs can provide width while two strikers occupy the opponent's centre-backs.");
                step.addRisk("Depends heavily on wing-backs", "If wing-backs are tired or pinned back, the team can lose width and attacking rhythm.");
                step.addRisk("Can be exposed behind wide players", "Opponents can attack the channels if the wing-backs push too high.");
                break;
            case FORMATION_3412:
                step.addAdvantage("Strong central attacking connection", "The attacking midfielder links with two forwards and gives the team a clear route through the middle.");
                step.addAdvantage("Good pressure on centre-backs", "Two forwards can press and occupy the opponent's defensive line.");
                step.addRisk("Narrow attacking shape", "The formation can rely too much on central play if wing-backs do not provide width.");
                step.addRisk("Space beside the back three", "Wide opponents can create problems if the outside centre-backs are dragged out.");
                break;
            default:
                step.addAdvantage("Good tactical fit", "The shape supports the main match conditions and the team's strengths.");
                step.addRisk("Requires role discipline", "If key players do not fit their roles, the shape can lose balance.");
                break;
        }
    }

    private static String display(Enum<?> value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Formation) {
            return ((Formation) value).getDisplayName();
        }
        String lower = value.name().toLowerCase().replace('_', ' ');
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}
