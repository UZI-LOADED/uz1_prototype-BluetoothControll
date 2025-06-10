package com.p4x.bluetooth.ui;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p4x.bluetooth.BluetoothController;
import com.p4x.bluetooth.R;

import java.util.ArrayList;
import java.util.List;

public class ScriptInjectorFragment extends Fragment {

    private EditText scriptInput;
    private Button sendButton;
    private Switch autoReplaySwitch;
    private RecyclerView logRecyclerView;
    private LogAdapter logAdapter;
    private List<String> logs = new ArrayList<>();

    private BluetoothController bluetoothController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_script_injector, container, false);
        scriptInput = root.findViewById(R.id.script_input);
        sendButton = root.findViewById(R.id.send_button);
        autoReplaySwitch = root.findViewById(R.id.auto_replay_switch);
        logRecyclerView = root.findViewById(R.id.log_recycler_view);

        logRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        logAdapter = new LogAdapter(logs);
        logRecyclerView.setAdapter(logAdapter);

        bluetoothController = new BluetoothController(getContext());

        sendButton.setOnClickListener(v -> {
            String script = scriptInput.getText().toString();
            if (!script.isEmpty()) {
                sendScript(script);
            }
        });

        return root;
    }

    private void sendScript(String script) {
        // For demo, just log commands. Extend to send commands via controller.
        logs.add("[SENT]: " + script);
        logAdapter.notifyDataSetChanged();
        // Parse and send script commands line by line here...
    }

    // Adapter class for logs RecyclerView
    private static class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {
        private final List<String> logs;

        public LogAdapter(List<String> logs) {
            this.logs = logs;
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView tv = new TextView(parent.getContext());
            tv.setMovementMethod(new ScrollingMovementMethod());
            return new LogViewHolder(tv);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            holder.textView.setText(logs.get(position));
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }

        static class LogViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public LogViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = (TextView) itemView;
            }
        }
    }
}
