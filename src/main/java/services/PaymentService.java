package services;

import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.TodoPagoConector;
import model.Course;
import model.Payment;
import model.Unit;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.jws.soap.SOAPBinding;
import java.net.MalformedURLException;
import java.util.*;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class PaymentService {

    private TodoPagoConector tpc;
    private UserService userService;
    private CourseService courseService;
    private SessionFactory sf;

    public PaymentService() throws MalformedURLException {
        this.sf = SessionFactoryManager.getInstance();
        userService = new UserService();
        courseService = new CourseService();

        Map<String, List<String>> parameters = new HashMap<String, List<String>>();
        parameters.put(ElementNames.Authorization, Collections.singletonList("PRISMA A793D307441615AF6AAAD7497A75DE59"));
        this.tpc = new TodoPagoConector(TodoPagoConector.developerEndpoint, parameters,true);
    }

    public Payment savePayment(int consumerId, int courseId, double amount) {
        Session session  = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Payment payment = new Payment(amount,new Date(),this.userService.getUser(consumerId), this.courseService.getCourse(courseId));
        session.save(payment);
        transaction.commit();
        session.close();
        return payment;
    }


    public Map<String,String> initiatePayment(int userId, int courseId){
        final User user = userService.getUser(userId);
        final Course course = courseService.getCourse(courseId);
        Map<String, Object> response = tpc.sendAuthorizeRequest(getParameters(course), getFraudControl(user));

        Map<String,String> resultMap =new HashMap<String,String>();
        for (Map.Entry<String, Object>  entry: response.entrySet()) {
            if(entry.getValue() instanceof String){
                resultMap.put(entry.getKey(), entry.getValue().toString());
            }
        }
        String requestKeyVal = resultMap.get("RequestKey");

        user.setRequestKey(requestKeyVal);
        userService.updateUser(user); //Se guarda la requestKey

        return resultMap;

    }

    public Map<String,Object> verifyPayment(int userId, String answerKey) {
        final User user = userService.getUser(userId);

        Map<String,String> parameters = new HashMap<>();
        parameters.put(ElementNames.Security,"A793D307441615AF6AAAD7497A75DE59");
        parameters.put(ElementNames.Merchant,"2159");
        parameters.put(ElementNames.RequestKey,user.getRequestKey());
        parameters.put(ElementNames.AnswerKey,answerKey);
        return tpc.getAuthorizeAnswer(parameters);
    }

    private Map<String,String> getFraudControl(User user) {
        Map<String, String> fraud = new HashMap<>();
        fraud.put("CSBTCITY" , "Villa General Belgrano");
        fraud.put("CSBTCOUNTRY", "AR");

        fraud.put("CSBTEMAIL", user.getMail());

        fraud.put("CSBTFIRSTNAME", user.getName());
        fraud.put("CSBTLASTNAME" , user.getSurname());

        fraud.put("CSBTPHONENUMBER" , "5411186895");
        fraud.put("CSBTPOSTALCODE", "1010");
        fraud.put("CSBTSTATE" , "B");
        fraud.put("CSBTSTREET1", "Some street");
        fraud.put("CSBTCUSTOMERID" , "453458");
        fraud.put("CSBTIPADDRESS" , "192.0.0.4");
        fraud.put("CSPTCURRENCY" , "ARS");
        fraud.put("CSPTGRANDTOTALAMOUNT" , "125.34");
        //Retai);
        fraud.put("CSSTCITY" , "Villa General Belgrano");
        fraud.put("CSSTCOUNTRY" , "AR");
        fraud.put("CSSTEMAIL" , "agbettati@bedes.com.ar");
        fraud.put("CSSTFIRSTNAME" , "Juan");
        fraud.put("CSSTLASTNAME" , "Perez");
        fraud.put("CSSTPHONENUMBER" , "541160913988");
        fraud.put("CSSTPOSTALCODE" , "1010");
        fraud.put("CSSTSTATE" , "B");
        fraud.put("CSSTSTREET1" , "Some Street 2153");
        fraud.put("CSITPRODUCTCODE" , "electronic_good");
        fraud.put("CSITPRODUCTDESCRIPTION" , "Test Prd Description");
        fraud.put("CSITPRODUCTNAME" , "TestPrd");
        fraud.put("CSITPRODUCTSKU" , "SKU1234");
        fraud.put("CSITTOTALAMOUNT" , "10.01");
        fraud.put("CSITQUANTITY","1");
        fraud.put("CSITUNITPRICE","10.01");
        return fraud;
    }

    private Map<String,String> getParameters(Course course) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(ElementNames.Endpoint, "https://developers.todopago.com.ar/services/t/1.1/");
        parameters.put(ElementNames.AuthorizeWSDL , "https://developers.todopago.com.ar/services/t/1.1/Authorize?wsdl");
        parameters.put(ElementNames.PaymentMethodsWSDL ,  "https://developers.todopago.com.ar/services/t/1.1/PaymentMethods?wsdl");
        parameters.put(ElementNames.OperationsWSDL ,  "https://developers.todopago.com.ar/services/t/1.1/Operations?wsdl");
        parameters.put(ElementNames.Session, "ABCDEF-1234-12221-FDE1-00000200");
        parameters.put(ElementNames.Security ,  "A793D307441615AF6AAAD7497A75DE59");
        parameters.put(ElementNames.EncodingMethod,  "XML");
        parameters.put(ElementNames.Merchant,  "2159"); //dato fijo (número identificador del comercio);
        parameters.put(ElementNames.OperationID ,  "8004"); //número único que identifica la operación, generado por el comercio
        parameters.put(ElementNames.CurrencyCode ,  "032");

        parameters.put(ElementNames.Amount ,  course.getPrice() + "");

        parameters.put(ElementNames.UrlOK ,  "http://localhost:4200/payment/" + course.getId());
        parameters.put(ElementNames.UrlError ,  "http://localhost:4200/payment/" + course.getId());
        parameters.put(ElementNames.EMAILCLIENTE ,  "bettatiagustin@gmail.com");
        parameters.put(ElementNames.MAXINSTALLMENTS ,  "12");
        parameters.put(ElementNames.MININSTALLMENTS ,  "1");
        parameters.put(ElementNames.TIMEOUT ,  "1800000");
        parameters.put(ElementNames.DISTRIBUTEDMERCHANT ,  "3#20");
        parameters.put(ElementNames.DISTRIBUTEDAMOUNT ,  "10.00#15.00");

        return parameters;
    }


}
