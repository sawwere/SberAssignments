package com.sawwere.sber.homework4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ContentReader {
    public String readContent(String link) throws IOException {
        URL url = new URL(link);

        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append('\n');
                line = br.readLine();
            }
        }
        return sb.toString();
    }
}
