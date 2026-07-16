import { notFound } from "next/navigation";
import Link from "next/link";
import { PLAYERS, COURTS, MATCHES, LEVEL_COLORS, AVAILABILITY_LABELS } from "@/lib/data";

export default async function PlayerPage({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;
  const player = PLAYERS.find((p) => p.id === id);
  if (!player) notFound();

  const preferredCourts = COURTS.filter((c) => player.preferredCourts.includes(c.id));
  const playerMatches = MATCHES.filter(
    (m) => m.player1Id === id || m.player2Id === id
  );
  const winRate =
    player.matchesPlayed > 0
      ? Math.round((player.wins / player.matchesPlayed) * 100)
      : 0;

  return (
    <div className="max-w-2xl mx-auto space-y-6">
      <Link href="/joueurs" className="text-emerald-600 hover:underline text-sm">
        ← Retour aux joueurs
      </Link>

      {/* Profile card */}
      <div className="bg-white rounded-2xl p-8 shadow-sm border border-gray-100">
        <div className="flex items-center gap-5 mb-6">
          <div className="w-20 h-20 rounded-full bg-emerald-100 text-emerald-700 font-bold text-3xl flex items-center justify-center">
            {player.avatar}
          </div>
          <div>
            <h1 className="text-2xl font-bold text-gray-800">{player.name}</h1>
            <p className="text-gray-500">{player.age} ans · Dakar</p>
            <span
              className={`inline-block mt-1 text-xs font-medium px-2.5 py-1 rounded-full capitalize ${LEVEL_COLORS[player.level]}`}
            >
              {player.level}
            </span>
          </div>
        </div>

        <p className="text-gray-600 mb-6">{player.bio}</p>

        {/* Stats */}
        <div className="grid grid-cols-3 gap-4 mb-6">
          {[
            { label: "Matchs joués", value: player.matchesPlayed },
            { label: "Victoires", value: player.wins },
            { label: "Taux de victoire", value: `${winRate}%` },
          ].map((stat) => (
            <div key={stat.label} className="text-center bg-gray-50 rounded-xl p-3">
              <div className="text-xl font-bold text-emerald-600">{stat.value}</div>
              <div className="text-xs text-gray-500">{stat.label}</div>
            </div>
          ))}
        </div>

        {/* Disponibilités */}
        <div className="mb-5">
          <h3 className="text-sm font-semibold text-gray-700 mb-2">Disponibilités</h3>
          <div className="flex flex-wrap gap-2">
            {player.availabilities.map((a) => (
              <span key={a} className="text-sm px-3 py-1 bg-emerald-50 text-emerald-700 rounded-full">
                {AVAILABILITY_LABELS[a]}
              </span>
            ))}
          </div>
        </div>

        {/* Contact */}
        <div className="pt-4 border-t border-gray-100">
          <a
            href={`https://wa.me/${player.phone.replace(/\s+/g, "").replace("+", "")}`}
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex items-center gap-2 bg-emerald-600 text-white font-semibold px-5 py-2.5 rounded-xl hover:bg-emerald-700 transition-colors"
          >
            <span>💬</span>
            Contacter sur WhatsApp
          </a>
        </div>
      </div>

      {/* Terrains préférés */}
      {preferredCourts.length > 0 && (
        <div className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100">
          <h2 className="text-lg font-bold text-gray-800 mb-4">Terrains préférés</h2>
          <div className="space-y-3">
            {preferredCourts.map((court) => (
              <Link
                key={court.id}
                href={`/terrains#${court.id}`}
                className="flex items-center justify-between p-3 rounded-xl bg-gray-50 hover:bg-emerald-50 transition-colors"
              >
                <div>
                  <div className="font-medium text-gray-800">{court.name}</div>
                  <div className="text-xs text-gray-500">{court.neighborhood}</div>
                </div>
                <span className="text-xs text-gray-400">
                  {court.pricePerHour.toLocaleString()} FCFA/h
                </span>
              </Link>
            ))}
          </div>
        </div>
      )}

      {/* Historique matchs */}
      {playerMatches.length > 0 && (
        <div className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100">
          <h2 className="text-lg font-bold text-gray-800 mb-4">Matchs récents</h2>
          <div className="space-y-3">
            {playerMatches.map((match) => {
              const opponentId = match.player1Id === id ? match.player2Id : match.player1Id;
              const opponent = PLAYERS.find((p) => p.id === opponentId);
              const court = COURTS.find((c) => c.id === match.courtId);
              return (
                <div key={match.id} className="flex items-center justify-between p-3 bg-gray-50 rounded-xl">
                  <div>
                    <div className="font-medium text-gray-800 text-sm">
                      vs {opponent?.name ?? "Inconnu"}
                    </div>
                    <div className="text-xs text-gray-500">
                      {match.date} · {match.time} · {court?.name}
                    </div>
                    {match.score && (
                      <div className="text-xs font-mono text-gray-600 mt-1">{match.score}</div>
                    )}
                  </div>
                  <span
                    className={`text-xs px-2.5 py-1 rounded-full font-medium ${
                      match.status === "confirmé"
                        ? "bg-blue-100 text-blue-700"
                        : match.status === "proposé"
                        ? "bg-yellow-100 text-yellow-700"
                        : match.status === "terminé"
                        ? "bg-gray-100 text-gray-600"
                        : "bg-red-100 text-red-700"
                    }`}
                  >
                    {match.status}
                  </span>
                </div>
              );
            })}
          </div>
        </div>
      )}
    </div>
  );
}
