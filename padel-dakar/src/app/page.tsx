import Link from "next/link";
import { PLAYERS, COURTS, MATCHES } from "@/lib/data";

export default function Home() {
  const upcomingMatches = MATCHES.filter((m) => m.status !== "terminé").length;

  return (
    <div className="space-y-12">
      {/* Hero */}
      <section className="text-center py-16 bg-gradient-to-br from-emerald-600 to-teal-700 rounded-2xl text-white">
        <div className="text-5xl mb-4">🎾</div>
        <h1 className="text-4xl font-bold mb-3">PadelDakar</h1>
        <p className="text-xl text-emerald-100 mb-8 max-w-lg mx-auto">
          Trouve ton partenaire de padel à Dakar et rejoins la communauté qui grandit !
        </p>
        <div className="flex gap-4 justify-center flex-wrap">
          <Link
            href="/joueurs"
            className="bg-white text-emerald-700 font-semibold px-6 py-3 rounded-xl hover:bg-emerald-50 transition-colors"
          >
            Trouver un joueur
          </Link>
          <Link
            href="/terrains"
            className="border-2 border-white text-white font-semibold px-6 py-3 rounded-xl hover:bg-white/10 transition-colors"
          >
            Voir les terrains
          </Link>
        </div>
      </section>

      {/* Stats */}
      <section className="grid grid-cols-3 gap-6">
        {[
          { label: "Joueurs inscrits", value: PLAYERS.length, icon: "👥" },
          { label: "Terrains à Dakar", value: COURTS.length, icon: "🏟️" },
          { label: "Matchs à venir", value: upcomingMatches, icon: "📅" },
        ].map((stat) => (
          <div
            key={stat.label}
            className="bg-white rounded-xl p-6 text-center shadow-sm border border-gray-100"
          >
            <div className="text-3xl mb-2">{stat.icon}</div>
            <div className="text-3xl font-bold text-emerald-600">{stat.value}</div>
            <div className="text-gray-500 text-sm mt-1">{stat.label}</div>
          </div>
        ))}
      </section>

      {/* Derniers joueurs */}
      <section>
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-2xl font-bold text-gray-800">Joueurs récents</h2>
          <Link href="/joueurs" className="text-emerald-600 font-medium hover:underline">
            Voir tous →
          </Link>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {PLAYERS.slice(0, 4).map((player) => (
            <Link
              key={player.id}
              href={`/joueurs/${player.id}`}
              className="bg-white rounded-xl p-5 shadow-sm border border-gray-100 hover:shadow-md hover:border-emerald-200 transition-all"
            >
              <div className="flex items-center gap-3 mb-3">
                <div className="w-12 h-12 rounded-full bg-emerald-100 text-emerald-700 font-bold text-lg flex items-center justify-center">
                  {player.avatar}
                </div>
                <div>
                  <div className="font-semibold text-gray-800">{player.name}</div>
                  <div className="text-sm text-gray-500">{player.age} ans</div>
                </div>
              </div>
              <span
                className={`text-xs font-medium px-2 py-1 rounded-full capitalize ${
                  player.level === "débutant"
                    ? "bg-green-100 text-green-700"
                    : player.level === "intermédiaire"
                    ? "bg-blue-100 text-blue-700"
                    : player.level === "avancé"
                    ? "bg-orange-100 text-orange-700"
                    : "bg-red-100 text-red-700"
                }`}
              >
                {player.level}
              </span>
            </Link>
          ))}
        </div>
      </section>

      {/* CTA terrains */}
      <section className="bg-white rounded-2xl p-8 border border-gray-100 shadow-sm flex items-center justify-between flex-wrap gap-4">
        <div>
          <h2 className="text-xl font-bold text-gray-800 mb-1">
            {COURTS.length} clubs de padel à Dakar
          </h2>
          <p className="text-gray-500">Almadies, Keur Gorgui, Amitié, Mermoz et plus…</p>
        </div>
        <Link
          href="/terrains"
          className="bg-emerald-600 text-white font-semibold px-5 py-2.5 rounded-xl hover:bg-emerald-700 transition-colors"
        >
          Explorer les terrains
        </Link>
      </section>
    </div>
  );
}
