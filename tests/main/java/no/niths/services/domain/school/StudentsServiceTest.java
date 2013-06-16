package main.java.no.niths.services.domain.school;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Student;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.domain.school.interfaces.StudentsService;
import main.java.no.niths.statics.TestVariables;

import static com.android.volley.Response.ErrorListener;
import static com.android.volley.Response.Listener;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by elotin on 17.06.13.
 */
@RunWith(RobolectricTestRunner.class)
public class StudentsServiceTest {
    private StudentsService mStudentsService;

    @Before
    public void setUp() throws Exception {
        TokenBundle bundle = mock(TokenBundle.class);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", TestVariables.AuthorizationValue);

        when(bundle.getAsHeaders()).thenReturn(headers);
        RequestQueue requestQueue = Volley.newRequestQueue(Robolectric.application);

        mStudentsService = new StudentsServiceImpl(bundle, requestQueue, Robolectric.application);
    }

    @Test
    public void testGetStudentByEmail() throws Exception {
        final Student[] student = {null};
        mStudentsService.getById(26, new Listener<Student>() {
            @Override
            public void onResponse(Student studentReturned) {
                student[0] = studentReturned;
                assertEquals((Object) 26, student[0].getId());

            }
        }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        fail("no student returned, error: " + volleyError.getLocalizedMessage());
                    }
                });
    }
}
