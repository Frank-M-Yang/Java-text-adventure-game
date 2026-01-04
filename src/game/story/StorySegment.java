package game.story;

public class StorySegment {

    public void showIntro() {
        System.out.println("Who Am I - Text Adventure Game\n");

        System.out.println("You wake up on a vast grassland...");
        System.out.println("Your head is throbbing, and you have no memory of who you are or why you're here.\n");

        System.out.println("You check your body and find that the chip slots meant for storing memories are empty!");
        System.out.println("All your memory chips are gone!\n");

        System.out.println("At this moment, a group of indigenous people approaches...");
        System.out.println("Tribal Elder: \"Outsider, I see your predicament.\"");
        System.out.println("Tribal Elder: \"Your chips were taken by an evil man and scattered in four directions.\"\n");

        System.out.println("The Tribal Elder says gravely:");
        System.out.println("\"Take special note: The South is the domain of the Sphinx, the most dangerous!\"");
        System.out.println("\"If you fail in the South, the Sphinx's curse will make you lose everything and start over!\"\n");

        System.out.println("==========================================\n");
    }

    public void showFinalStory() {
        System.out.println("You've collected all the memory chips!\n");

        System.out.println("As the chips are reinserted into your brain, memories flood back like a tidal wave...\n");

        System.out.println("You remember everything:");
        System.out.println("You are Jackie Chan, an enhanced human agent created by the Evil Doctor.");
        System.out.println("Your mission was to execute secret intelligence operations and steal treasures all over the world for the doctor.\n");

        System.out.println("But during the last mission...");
        System.out.println("The doctor wanted to keep all the treasures for himself and tried to eliminate you!");
        System.out.println("He erased your memories and abandoned you in this grassland.\n");

        System.out.println("Suddenly, a sinister laugh echoes...");
        System.out.println("Evil Doctor: \"Hahaha! I never thought you'd recover your memories!\"");
        System.out.println("Evil Doctor: \"But this changes nothing, you're still my creation!\"\n");
    }

    public void showChipObtained(String direction, int current, int total) {
        System.out.println("\n★ Memory chip obtained from " + direction + "!");
        System.out.println("Progress: " + current + "/" + total);
    }

    public void showAllChipsCollected() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║  ALL MEMORY CHIPS COLLECTED!       ║");
        System.out.println("║  Final challenge awaits...         ║");
        System.out.println("╚════════════════════════════════════╝");
    }

    public void showSphinxCurse() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    THE SPHINX'S CURSE STRIKES!         ║");
        System.out.println("║    All your memories fade away...      ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    public void showSphinxCurseEffect() {
        System.out.println("\nThe Sphinx's curse takes effect...");
        System.out.println("Your mind goes blank, all memories lost.");
        System.out.println("You wander aimlessly until your systems shut down.");
    }

    public void showFinalChallengeIntro() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      FINAL CHALLENGE BEGINS!           ║");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.println("\nThe Evil Doctor stands before you in his secret lab.");
        System.out.println("Doctor: \"So you've recovered all your memory chips...\"");
        System.out.println("Doctor: \"But your ultimate test awaits!\"");
        System.out.println("Doctor: \"Solve this final puzzle to prove you're worthy!\"");
    }

    public void showDoctorDefeated() {
        System.out.println("\nDoctor: \"Incredible! You solved it!\"");
        System.out.println("Doctor: \"You truly are my greatest creation!\"");
    }

    public void showDoctorDealIntro() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                                                    ║");
        System.out.println("║         THREE FAILURES. YOUR END IS NEAR.          ║");
        System.out.println("║                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
    }

    public void showDoctorDealOffer() {
        System.out.println("\nThe Doctor's eyes gleam with malice...");
        System.out.println("\nDoctor: \"Wait. I have a proposition for you.\"");
        System.out.println("Doctor: \"You've proven yourself... somewhat useful.\"");
        System.out.println("Doctor: \"Surrender your memory chips to me...\"");
        System.out.println("Doctor: \"And I will spare your life.\"");
        System.out.println("Doctor: \"You will serve me forever, without memory, without pain.\"");
        System.out.println("Doctor: \"Refuse... and I will terminate you right now.\"");
    }

    public void showDoctorDealOptions() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                                                    ║");
        System.out.println("║              YOUR FINAL CHOICE                     ║");
        System.out.println("║                                                    ║");
        System.out.println("║  1. Accept the deal (Surrender memory chips)       ║");
        System.out.println("║  2. Refuse the deal (Face termination)             ║");
        System.out.println("║                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
    }

    public void showDealAcceptance_Part1() {
        System.out.println("\n...");
    }

    public void showDealAcceptance_Part2() {
        System.out.println("\nYou slowly extend your hand...");
        System.out.println("The memory chips slip from your grasp into the Doctor's palm.");
    }

    public void showDealAcceptance_Part3() {
        System.out.println("\nDoctor: \"Excellent choice. Very... pragmatic.\"");
        System.out.println("\nAs the chips leave your possession, a strange emptiness fills you.");
        System.out.println("Your memories begin to fade...");
        System.out.println("Your name... your purpose... all dissolving into nothingness...");
    }

    public void showDealAcceptance_Part4() {
        System.out.println("\nDoctor: \"Welcome to eternal servitude, my perfect creation.\"");
        System.out.println("Doctor: \"You will obey. You will serve. You will never question.\"");
        System.out.println("\nThe Doctor's laughter echoes as darkness consumes your consciousness...");
    }

    public void showDealRefusal_Part1() {
        System.out.println("\n...");
    }

    public void showDealRefusal_Part2() {
        System.out.println("\n\"No,\" you say firmly.");
        System.out.println("\"I'd rather die than be your puppet.\"");
    }

    public void showDealRefusal_Part3() {
        System.out.println("\nThe Doctor's face darkens.");
        System.out.println("Doctor: \"So be it. Foolish... to the very end.\"");
        System.out.println("\nHe raises his weapon...");
    }

    public void showDealRefusal_Part4() {
        System.out.println("\nA blinding flash.");
        System.out.println("Then... nothing.");
    }

    public void showDealRefusal_Part5() {
        System.out.println("\nYou chose freedom over servitude.");
        System.out.println("Even if it meant death.");
    }

    public void showVictoryOptions() {
        System.out.println("\nDoctor kneels before you, defeated.");
        System.out.println("Now you must decide your fate...");

        System.out.println("\n1. Kill the doctor and take his place as the new Dark Lord");
        System.out.println("2. Listen to the doctor's final words...");
        System.out.println("3. Turn the doctor over to INTERPOL and return the stolen treasures");
    }

    public void showDarkLordPath() {
        System.out.println("\nYou stand over the defeated doctor...");
        System.out.println("\"You had your chance,\" you say coldly.");
        System.out.println("With one swift motion, you end the doctor's reign.");
        System.out.println("You sit upon the throne of darkness.");
        System.out.println("The doctor's minions bow before their new master.");
        System.out.println("\nDoctor's final whisper: \"Remember... power corrupts...\"");
    }

    public void showEternalServantPathIntro() {
        System.out.println("\nYou hesitate, weapon raised but not striking.");
        System.out.println("\nDoctor smirks: \"You think you can escape your nature?\"");
        System.out.println("Doctor: \"You were built for dirty work!\"");
        System.out.println("Doctor: \"Do you really think INTERPOL will spare a killing machine like you?\"");
    }

    public void showEternalServantEnding() {
        System.out.println("\nYou hand over the memory chips...");
        System.out.println("A strange emptiness fills you as your memories fade.");
        System.out.println("\nThe doctor's voice echoes:");
        System.out.println("\"Welcome to eternal servitude, my perfect creation...\"");
    }

    public void showEternalServantRefusal() {
        System.out.println("\nYou refuse to surrender!");
        System.out.println("But doubt has been planted in your mind...");
        System.out.println("Doctor: \"It doesn't matter. The seeds are sown. You'll always wonder...\"");
    }

    public void showRedemptionPathIntro() {
        System.out.println("\nYou lower your weapon.");
        System.out.println("\"No,\" you say firmly. \"This ends now.\"");
        System.out.println("\nYou call INTERPOL and hand the doctor over to authorities.");
        System.out.println("With the doctor captured, you gather all the stolen artifacts.");
        System.out.println("You return them to their rightful owners - the indigenous tribes.");
    }

    public void showRedemptionChoice() {
        System.out.println("\nThe tribal elder approaches you:");
        System.out.println("\"You have returned what was stolen and captured the thief.\"");
        System.out.println("\"Our home is open to you. Stay here, find peace.\"");
    }

    public void showRedemptionAccepted() {
        System.out.println("\nYou accept their kindness and settle in the savannah.");
        System.out.println("For the first time, you find true peace...");
    }

    public void showRedemptionDeclined() {
        System.out.println("\nYou thank them but choose to walk your own path...");
    }
}