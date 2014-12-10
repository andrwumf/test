package com.template.mlevytskiy.ui;

import android.support.v4.view.PagerAdapter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.template.mlevytskiy.R;

/**
 * Created by Макс on 01.05.2014.
 */
public class StoriesPagerAdapter extends PagerAdapter {

    private String[] mStoryArray = new String[] {
            "TAKE (took, TAKen). You SHAKE (shook, SHAken). \n" +
                    "WAKE (woke, WOken) to the STYLE Im creAting. \n" +
                    "THINK (thought, THOUGHT). SEEK (sought, SOUGHT).\n" +
                    "LISten to the LESson that I TEACH (taught, TAUGHT).\n" +
                    "\n" +
                    "Dont SLEEP (slept, SLEPT). I CREEP (crept, CREPT). \n" +
                    "I SNEAK (snuck, SNUCK UP). You LEAP (leapt, LEAPT).\n" +
                    "I KEEP (kept, KEPT) HAVing FUN. \n" +
                    "Im never BEAT (beat, BEAten); I WIN (won, WON).\n" +
                    "DO (did, DONE). BeGIN (began, beGUN).\n" +
                    "SHOOT (shot, SHOT)—no, I DONT own a GUN. \n" +
                    "I LEAD (led, LED) so I can FEED (fed, FED). \n" +
                    "the KNOWledge you NEED, STRAIGHT to your HEAD. \n" +
                    "When I BRING (brought, BROUGHT) it, you CATCH (caught, CAUGHT) it.\n" +
                    "Sit BACKreLAX. Dont FIGHT (fought, FOUGHT) it.\n" +
                    "\n" +
                    "Please don't \n" +
                    "\n" +
                    "FREEZE (froze, FROzen) when I SPEAK (spoke, SPOken). \n" +
                    "Its REAL. You can FEEL I dont STEAL (stole, STOlen). \n" +
                    "I CHOOSE (chose, CHOsen) the VERy best RHYMES and\n" +
                    "WRITE (wrote, WRITten) them INto my LINES and\n" +
                    "INto your MIND. When we MEET (met, MET)\n" +
                    "Ill BET (bet, BET) I wont LET you forGET \n" +
                    "(forGOTforGOTten). I GET (got, GOTten)\n" +
                    "EVery head NODding. Dont THINK about STOPping\n" +
                    "just COME (came, COME). \n" +
                    "THIS is hip hop. I dont SING (sang, SUNG).\n" +
                    "I STING (stung, STUNG). I CLING (clung, CLUNG).\n" +
                    "On EACH and every WORD, you HANG (hung, HUNG).\n" +
                    "\n" +
                    "Its not enough to\n" +
                    "\n" +
                    "DREAM (dreamt, DREAMT); youve got to SPEND (spent, SPENT)\n" +
                    "TIME on your GOALS. Please LEND (lent, LENT) me your\n" +
                    "EAR. Come NEAR and Ill LAY (laid, LAID)\n" +
                    "DOWN this new SOUND that I MAKE (made, MADE). \n" +
                    "I HOPE you dont SAY that you THINK its JUNK.\n" +
                    "I HOPE you dont THINK that I STINK (stank, STUNK).\n" +
                    "If youre THIRSty for ENGlish, come DRINK (drank, DRUNK).\n" +
                    "because I SINK (sank, SUNK) ALL compeTItion when they \n" +
                    "HEAR (heard, HEARD) that I GIVE (gave, GIVen)\n" +
                    "encouragement when I SPIT (spat, SPAT).\n" +
                    "Never QUIT (quit, QUIT); dont SIT (sat, SAT).\n" +
                    "Yeah, I LIKE it like THAT. Ill even KNEEL (knelt, KNELT).\n" +
                    "and BEG you to exPRESS what you FEEL (felt, FELT).\n" +
                    "\n" +
                    "I RISE (rose, RISen) when I DRIVE (drove, DRIVen) through \n" +
                    "the BEAT; tap your FEET as you RIDE (rode, RIDden). \n" +
                    "Those that HIDE (hid, HIDden) I FIND (found, FOUND).\n" +
                    "If you FLEE (fled, FLED) then Ill TRACK you DOWN.\n" +
                    "\n" +
                    "Now you SEE (saw, SEEN) that I MEAN (meant, MEANT) \n" +
                    "every WORD of the MESsage that I SEND (sent, SENT). \n" +
                    "I SHOW (showed, SHOWN) I can FLY (flew, FLOWN).\n" +
                    "Now you KNOW (knew, KNOWN) I SHINE (shone, SHONE).\n" +
                    "Ill THROW (threw, THROWN) you the BALL. Its your TURN. \n" +
                    "GROW (grew, GROWN) with the VERBS that youve LEARNED. \n" +
                    "GRAMmar through LYRics I DRAW (drew, DRAWN). \n" +
                    "PEACE to elLS, now I GO (went, GONE)!",

                    "If you learn this 11 verbs, you will know 50% of more popular irregular verbs!" + "\n" +
                    "say" + "\n" +
                    "make" + "\n" +
                    "go" + "\n" +
                    "take" + "\n" +
                    "come" + "\n" +
                    "see" + "\n" +
                    "know" + "\n" +
                    "get" + "\n" +
                    "give" + "\n" +
                    "find" + "\n" +
                    "think"
    };

    @Override
    public Object instantiateItem(ViewGroup collection, final int i) {
        final View view;
        String text = mStoryArray[i];
        if (i == 0) {
            view = View.inflate(collection.getContext(), R.layout.view_pager_item_story_rap, null);
            text = text.toLowerCase().replace(" i ", " I ").replace(".i ", ".I ").replace("im", "I'm");
        } else {
            view = View.inflate(collection.getContext(), R.layout.view_pager_item_stories, null);
            ((TextView) view.findViewById(R.id.title)).setText("11 the most popular irregular verbs");
        }
        TextView story = (TextView) view.findViewById(R.id.story);
        story.setText(text);
        story.setMovementMethod(new ScrollingMovementMethod());
        collection.addView(view, 0);
        return view;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    public int getItemPosition(Object object) {
        return POSITION_UNCHANGED;
    }
}
