import type { Metadata } from "next";
import "./globals.css";
import NavBar from "@/components/NavBar";

export const metadata: Metadata = {
  title: "PadelDakar - Trouve ton partenaire de padel",
  description: "Mise en relation de joueurs de padel à Dakar, Sénégal",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="fr">
      <body className="min-h-screen bg-gray-50">
        <NavBar />
        <main className="max-w-6xl mx-auto px-4 py-8">{children}</main>
        <footer className="mt-16 py-6 text-center text-gray-500 text-sm border-t border-gray-200 bg-white">
          © 2026 PadelDakar — Dakar, Sénégal 🇸🇳
        </footer>
      </body>
    </html>
  );
}
