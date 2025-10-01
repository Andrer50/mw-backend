package mweb.mw_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionDebugger implements CommandLineRunner {

    @Value("${SUPABASE_HOST:NOT_SET}")
    private String supabaseHost;

    @Value("${SUPABASE_DB:NOT_SET}")
    private String supabaseDb;

    @Value("${SUPABASE_USER:NOT_SET}")
    private String supabaseUser;

    @Value("${SUPABASE_PASSWORD:NOT_SET}")
    private String supabasePassword;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== DATABASE CONNECTION DEBUG ===");
        System.out.println("SUPABASE_HOST: " + (supabaseHost.equals("NOT_SET") ? "❌ NOT SET" : "✅ " + supabaseHost));
        System.out.println("SUPABASE_DB: " + (supabaseDb.equals("NOT_SET") ? "❌ NOT SET" : "✅ " + supabaseDb));
        System.out.println("SUPABASE_USER: " + (supabaseUser.equals("NOT_SET") ? "❌ NOT SET" : "✅ " + supabaseUser));
        System.out.println("SUPABASE_PASSWORD: " + (supabasePassword.equals("NOT_SET") ? "❌ NOT SET" : "✅ [HIDDEN]"));
        System.out.println("=================================");
    }
}