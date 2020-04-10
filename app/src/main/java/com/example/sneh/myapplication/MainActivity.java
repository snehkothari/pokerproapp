package com.example.sneh.myapplication;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Random;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    int strengthofhand;
    String u1, u2;
    Vector cardsopen = new Vector();
    Vector communitycards = new Vector();
    Vector cardsforML = new Vector();
    int us, nc;
    int ch;
    int p1c1;
    int p;
    int s;
    int player_no = 2;
    int[] pc1 = new int[player_no];
    int[] pc2 = new int[player_no];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.player1);
        imageView.setImageResource(R.drawable.player1);

        gamestart();
    }

    public void players() {
    }

    public void gamestart() {
        ImageView viewToUse = findViewById(R.id.card1);
        viewToUse.setImageResource(android.R.color.transparent);
        viewToUse = findViewById(R.id.card2);
        viewToUse.setImageResource(android.R.color.transparent);
        viewToUse = findViewById(R.id.card3);
        viewToUse.setImageResource(android.R.color.transparent);
        viewToUse = findViewById(R.id.card4);
        viewToUse.setImageResource(android.R.color.transparent);
        viewToUse = findViewById(R.id.card5);
        viewToUse.setImageResource(android.R.color.transparent);
        ch = 0;
        s = 50;
        p = 100;
        pc1[0] = 0;
        pc1[1] = 0;
        strengthofhand = -1;
        cardsforML.clear();
        cardsopen.clear();
        communitycards.clear();
        p1c1 = drawCard();
        ImageView imageView9 = findViewById(R.id.pcard1);
        imageView9.setImageResource(p1c1);
        u1 = getResources().getResourceEntryName(p1c1);
        u1 = u1.replaceAll("pc", "");
        cardsforML.add(getResources().getResourceEntryName(p1c1));
        cardsopen.add(getResources().getResourceEntryName(p1c1));
        p1c1 = drawCard();
        imageView9 = findViewById(R.id.pcard2);
        imageView9.setImageResource(p1c1);
        u2 = getResources().getResourceEntryName(p1c1);
        u2 = u2.replaceAll("pc", "");
        cardsforML.add(getResources().getResourceEntryName(p1c1));
        cardsopen.add(getResources().getResourceEntryName(p1c1));
        p1c1 = drawCard();
        cardsopen.add(getResources().getResourceEntryName(p1c1));
        p1c1 = drawCard();
        cardsopen.add(getResources().getResourceEntryName(p1c1));
        imageView9 = findViewById(R.id.player2);
        imageView9.setImageResource(R.drawable.gray_back);
        imageView9 = findViewById(R.id.player3);
        imageView9.setImageResource(R.drawable.gray_back);
        TextView str = findViewById(R.id.strength);
        str.setText("Hand Strength ");
        str = findViewById(R.id.type);
        str.setText("Hand Type ");

    }

    public String[] urlgenerators() {
        String[] urls = new String[5];
        int j = 0;
        for (int i = 0; i < cardsforML.size() - 1; i++) {
            StringBuilder sb = new StringBuilder(100);
            for (j = 0; j < cardsforML.size(); j++) {
                if (j != i) {
                    sb.append(cardsforML.get(j).toString());
                }
            }
            urls[i] = sb.toString();
            Log.i("U", urls[i]);
        }
        return (urls);

    }

    public String[] urlgenerator2() {
        int counter = 0;
        Log.i("cardsformllllll", cardsforML.toString());
        String urlstemp = cardsforML.get(cardsforML.size() - 2).toString();
        urlstemp = urlstemp.concat(cardsforML.get(cardsforML.size() - 1).toString());
        Log.i("cardsforML67", urlstemp);
        String[] urls = new String[10];
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                for (int k = j + 1; k < 5; k++) {
                    StringBuilder sb = new StringBuilder(100);
                    sb.append(cardsforML.get(i).toString());
                    sb.append(cardsforML.get(j).toString());
                    sb.append(cardsforML.get(k).toString());
                    sb.append(urlstemp);
                    urls[counter] = sb.toString();
                    counter++;
                    Log.i("URL", urls[i]);
                }
            }
        }
        return (urls);
    }

    public String urlsetup(String c) {
        int i = 0;
        String temp = "";
        Log.i("Cards", c);
        String finalcards = "";
        while (i < c.length()) {
            if (c.charAt(i) == 'c') {
                finalcards = finalcards + "1|" + temp + "|";
                temp = "";
            } else if (c.charAt(i) == 'd') {
                finalcards = finalcards + "2|" + temp + "|";
                temp = "";
            } else if (c.charAt(i) == 'h') {
                finalcards = finalcards + "3|" + temp + "|";
                temp = "";
            } else if (c.charAt(i) == 's') {
                finalcards = finalcards + "4|" + temp + "|";
                temp = "";
            } else if (Character.isDigit(c.charAt(i))) {
                temp = temp + c.charAt(i);
            } else {
            }
            i++;
        }
        Log.i("Cards for url", finalcards);
        return (finalcards);
    }

    public void MLproperty(String card) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://pokerpro-for-deployment.herokuapp.com/?predict=";

        url = url + card;
        Log.i("url", url);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("response", (response));
                        if (strengthofhand < Integer.parseInt(response)) {
                            strengthofhand = Integer.parseInt(response);
                            Displayi(strengthofhand);
                            Log.i("RES", strengthofhand + " ");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView str = findViewById(R.id.strength);
                str.setText("Did not work ");

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void Displayi(int ht) {
        TextView strength_tv = findViewById(R.id.strength);
        TextView handtype_tv = findViewById(R.id.type);
        switch (ht) {
            case 0:
                strength_tv.setText("Very POOR");
                handtype_tv.setText("High Card");
                break;
            case 1:
                strength_tv.setText("POOR");
                handtype_tv.setText("One Pair");
                break;
            case 2:
                strength_tv.setText("POOR");
                handtype_tv.setText("Two Pair");
                break;
            case 3:
                strength_tv.setText("MODERATE");
                handtype_tv.setText("Three of a Kind");
                break;
            case 4:
                strength_tv.setText("MODERATE");
                handtype_tv.setText("Straight");
                break;
            case 5:
                strength_tv.setText("GOOD");
                handtype_tv.setText("Flush");
                break;
            case 6:
                strength_tv.setText("GOOD");
                handtype_tv.setText("Full House");
                break;
            case 7:
                strength_tv.setText("VERY GOOD");
                handtype_tv.setText("Four of a Kind");
                break;
            case 8:
                strength_tv.setText("EXCELLENT");
                handtype_tv.setText("Straight Flush");
                break;
            case 9:
                strength_tv.setText("WINNER");
                handtype_tv.setText("Royal Flush");
                break;
        }
    }

    public void gameplay(View view) {

        if (view.getId() == R.id.foldButton) {
            winnerdisplays(1);
        } else if (view.getId() == R.id.callButton) {
            ch++;
            TextView tv = findViewById(R.id.uaction);
            tv.setText("Call " + s);
            p = p + s;
            TextView tv2 = findViewById(R.id.paction);
            tv2.setText(" Player's  turn");
        } else if (view.getId() == R.id.raiseButton) {
            ch++;
            TextView tv = findViewById(R.id.uaction);
            s = s * 2;
            p = p + s;
            tv.setText("Raise " + s);
            TextView tv2 = findViewById(R.id.paction);
            tv2.setText(" Player's  turn");
        } else {
            ch++;
            TextView tv = findViewById(R.id.uaction);
            tv.setText("Check ");
            TextView tv2 = findViewById(R.id.paction);
            tv2.setText(" Player's  turn");
        }
        if (ch == 1) {
            firstthreeflops();
        } else if (ch == 2) {
            river1();
        } else if (ch == 3) {
            river2();
        } else {
            winnerdecide();
        }
    }

    public Vector sortcards(int i) {
        Vector t = new Vector();
        t.add((cardsopen.get(i).toString()).replace("pc", ""));
        t.add((cardsopen.get(i + 1).toString()).replace("pc", ""));
        for (int j = 0; j < communitycards.size(); j++) {
            t.add((communitycards.get(j).toString()).replace("pc", ""));
        }
        Log.i("ToSort Cards", t.toString());
        int tlength = t.size() - 1;
        String n1, n2;
        int ni1, ni2;
        for (int j = 0; j < tlength; j++) {
            for (int k = 0; k < tlength - j; k++) {
                n1 = (t.get(k).toString());
                Log.i("N1", n1);
                ni1 = Integer.parseInt(n1.substring(0, n1.length() - 1));
                n2 = (t.get(k + 1).toString());
                Log.i("N2", n2);
                ni2 = Integer.parseInt(n2.substring(0, n2.length() - 1));
                if (ni1 > ni2) {
                    t.set(k, n2);
                    t.set(k + 1, n1);
                }
            }
        }
        Log.i("t", t.toString());
        return (t);
    }

    public int checkpair(Vector v, int l) {
        int[] hr = new int[14];
        int rank = 1;
        int strai = 0;
        int flush = 0;
        int[] suit = new int[4];
        int rankcounter = 0;
        int pair1, pair2, threeofkind;
        threeofkind = 0;
        int hc = 0;
        pair2 = 0;
        pair1 = 0;
        for (int i = 0; i < v.size(); i++) {
            String n1 = v.get(i).toString();
            char s = n1.charAt(n1.length() - 1);
            Log.i("n1", n1);
            int ni1 = Integer.parseInt(n1.substring(0, n1.length() - 1));
            hr[ni1 - 1]++;
            if ((ni1 - 1) == 0) {
                hr[13]++;
            }
            switch (s) {
                case 'h':
                    suit[0]++;
                    break;
                case 'c':
                    suit[1]++;
                    break;
                case 'd':
                    suit[2]++;
                    break;
                case 's':
                    suit[3]++;
                    break;
            }

        }
        for (int i = hr.length - 1; i >= 0; i--) {
            if (hr[i] >= 1) {
                if (rank - 1 == i) {
                    rankcounter++;
                    if (rankcounter == 5) {
                        strai = 1;
                        hc = rank;
                    }
                    rank--;
                } else {
                    rank = i;
                    rankcounter = 1;
                }
            }
            if (hr[i] == 4) {
                return (7);
            } else if (hr[i] == 3) {
                pc1[l] = i;
                threeofkind = 3;
            } else if (hr[i] == 2 && pair1 == 0) {
                pair1 = 1;
                pc1[l] = i;
            } else if (hr[i] == 2 && pair1 != 0 && pair2 == 0) {
                pair2 = 1;
                pc2[l] = i;
            }

        }
        for (int i = 0; i < 4; i++) {
            if (suit[i] == 5) {
                flush = 1;
                break;
            }
        }

        if (strai == 1 && flush == 1 && hc == 9) {
            return 9;
        }
        if (strai == 1 && flush == 1) {
            return 8;
        }
        if (threeofkind == 3 && pair1 == 1) {
            return (6);
        }
        if (flush == 1) {
            return (5);
        }
        if (strai == 1) {
            return (4);
        }
        if (threeofkind == 3) {
            return (3);
        }
        if (pair1 == 1 && pair2 == 1) {
            return (2);
        } else if (pair1 == 1 && pair2 == 0) {
            return (1);
        } else {
            return (0);
        }
    }

    public void winnerdecide() {
        int m = checkpair((sortcards(0)), 0);
        Log.i("Sortcards & checkpair working", m + " ");
        int pl = 0;
        for (int i = 1; i < player_no; i++) {
            int c = checkpair(sortcards(i * 2), i);
            if (m < c) {
                pl = i;
                m = c;
            } else if (m == c) {
                if (pc1[pl] < pc1[i] || (pc1[pl] == pc1[i] && pc2[pl] < pc2[i])) {
                    pl = i;
                    m = c;
                } else {
                    pl = -1;
                }
            } else {
            }
            Resources resources = getResources();
            String nam = cardsopen.get(i * 2).toString();
            Log.i("Cards of opponent", nam);
            int resourceId = resources.getIdentifier(nam,
                    "drawable", getPackageName());
            ImageView iv = findViewById(R.id.player2);
            iv.setImageResource(resourceId);
            nam = cardsopen.get((i * 2) + 1).toString();
            Log.i("Cards of opponent", nam);
            resourceId = resources.getIdentifier(nam,
                    "drawable", getPackageName());
            ImageView iv2 = findViewById(R.id.player3);
            iv2.setImageResource(resourceId);
        }

        winnerdisplays(pl);

    }

    public void winnerdisplays(int n) {
        TextView tv = findViewById(R.id.type);
        tv.setText("Player " + n + " WINS");
        tv.setTextAppearance(R.style.winnerdisplay);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                gamestart();

            }
        }, 5000);
    }

    public void firstthreeflops() {
        p1c1 = drawCard();
        ImageView imageView4 = findViewById(R.id.card1);
        imageView4.setImageResource(p1c1);
        Log.i("TAG", "firstthreeflops: ");
        communitycards.add(getResources().getResourceEntryName(p1c1));
        cardsforML.add(getResources().getResourceEntryName(p1c1));
        p1c1 = drawCard();
        ImageView imageView5 = findViewById(R.id.card2);
        imageView5.setImageResource(p1c1);
        communitycards.add(getResources().getResourceEntryName(p1c1));
        cardsforML.add(getResources().getResourceEntryName(p1c1));
        p1c1 = drawCard();
        ImageView imageView6 = findViewById(R.id.card3);
        imageView6.setImageResource(p1c1);
        communitycards.add(getResources().getResourceEntryName(p1c1));
        cardsforML.add(getResources().getResourceEntryName(p1c1));
        String a = communitycards.toString();
        a = a.replaceAll("pc", "");
        String finalcards = urlsetup((u1.concat(u2)).concat(a));
        Log.i("finalcards", finalcards);
        MLproperty(finalcards);
        Log.i("TAG", strengthofhand + " ");
    }

    public void remove_pc() {
        String temp;
        for (int i = 0; i < cardsforML.size(); i++) {
            temp = cardsforML.get(i).toString();
            temp = temp.replace("pc", "");
            cardsforML.set(i, temp);
        }
    }

    public void river1() {
        p1c1 = drawCard();
        ImageView imageView7 = findViewById(R.id.card4);
        imageView7.setImageResource(p1c1);
        communitycards.add(getResources().getResourceEntryName(p1c1));
        cardsforML.add(getResources().getResourceEntryName(p1c1));
        remove_pc();
        String[] urls = new String[6];
        urls = urlgenerators();
        Log.i("CardsforML", cardsforML.toString());
        Log.i("Urls", urls[0]);
        for (int i = 0; i < urls.length; i++) {
            urls[i] = urlsetup(urls[i]);
            MLproperty(urls[i]);
        }

    }

    public void river2() {
        p1c1 = drawCard();
        ImageView imageView8 = findViewById(R.id.card5);
        imageView8.setImageResource(p1c1);
        cardsforML.remove(cardsforML.size() - 1);
        cardsforML.add(getResources().getResourceEntryName(p1c1));
        Log.i("cardsforml", cardsforML.toString());
        String temp = cardsforML.get(cardsforML.size() - 1).toString();
        Log.i("temp", temp);
        temp = temp.replace("pc", "");
        Log.i("temp", temp);
        cardsforML.set(cardsforML.size() - 1, temp);
        String[] urls = new String[11];
        urls = urlgenerators();
        for (int i = 0; i < 5; i++) {
            urls[i] = urlsetup(urls[i]);
            MLproperty(urls[i]);
        }
        cardsforML.add(communitycards.get(communitycards.size() - 1));
        temp = cardsforML.get(cardsforML.size() - 1).toString();
        temp = temp.replace("pc", "");
        Log.i("temp", temp);
        cardsforML.set(cardsforML.size() - 1, temp);
        Log.i("community", communitycards.toString());
        Log.i("cardsforml", cardsforML.toString());
        communitycards.add(getResources().getResourceEntryName(p1c1));
        urls = urlgenerator2();
        for (int i = 0; i < urls.length; i++) {
            urls[i] = urlsetup(urls[i]);
            MLproperty(urls[i]);
        }


    }

    public String RandomCardGenerator() {
        Random rand = new Random();
        int randint = rand.nextInt(13);
        randint = randint + 1;
        int randcolor = rand.nextInt(4);
        String imname;
        if (randcolor == 0) {
            imname = ("pc" + randint + "c");
        } else if (randcolor == 1) {
            imname = ("pc" + randint + "d");
        } else if (randcolor == 2) {
            imname = ("pc" + randint + "h");
        } else {
            imname = ("pc" + randint + "s");
        }
        return imname;

    }

    public int drawCard() {
        String imname = "00";
        int i = 0;
        while (i == 0) {
            imname = RandomCardGenerator();
            if (cardsopen.contains(imname) || communitycards.contains(imname)) {
                i = 0;
            } else {
                i = 1;
            }
        }
        Resources resources = getResources();
        int resourceId = resources.getIdentifier(imname,
                "drawable", getPackageName());
        return resourceId;
    }
}