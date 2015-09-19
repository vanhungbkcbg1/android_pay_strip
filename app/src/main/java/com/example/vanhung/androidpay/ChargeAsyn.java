package com.example.vanhung.androidpay;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vanhung on 19/09/2015.
 */
public class ChargeAsyn extends AsyncTask<Void, Void, Charge> {
    private String token;
    private Context context;

    public ChargeAsyn(String token, Context context) {
        this.token = token;
        this.context = context;
    }

    @Override
    protected Charge doInBackground(Void... params) {
        Charge result=null;
        com.stripe.Stripe.apiKey="sk_test_K1GjiS83T08V4NCizCNkCBLI";
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", 400);
        chargeParams.put("currency", "usd");
        chargeParams.put("source", token); // obtained with Stripe.js
        chargeParams.put("description", "Charge for test@example.com");

        try {
           result=Charge.create(chargeParams);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (CardException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Charge result) {
        super.onPostExecute(result);
        if (result==null)
        {
            showResult("Error");
        }else
        {
            if (result.getPaid())
            {
                showResult("Thanh toán thành công");
            }else
            {
                showResult(result.getFailureMessage());
            }
        }
    }
    private void showResult(String result)
    {
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
    }
}
