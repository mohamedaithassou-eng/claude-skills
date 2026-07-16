import Link from "next/link";
import { MATCHES, PLAYERS, COURTS } from "@/lib/data";

const STATUS_STYLES: Record<string, string> = {
  proposé: "bg-yellow-100 text-yellow-700",
  confirmé: "bg-blue-100 text-blue-700",
  terminé: "bg-gray-100 text-gray-600",
  annulé: "bg-red-100 text-red-700",
};

export default function MatchsPage() {
  const upcoming = MATCHES.filter((m) => m.status !== "terminé" && m.status !== "annulé");
  const past = MATCHES.filter((m) => m.status === "terminé" || m.status === "annulé");

  function MatchCard({ matchId }: { matchId: string }) {
    const match = MATCHES.find((m) => m.id === matchId)!;
    const p1 = PLAYERS.find((p) => p.id === match.player1Id);
    const p2 = PLAYERS.find((p) => p.id === match.player2Id);
    const court = COURTS.find((c) => c.id === match.courtId);

    return (
      <div className="bg-white rounded-xl p-5 shadow-sm border border-gray-100">
        <div className="flex items-center justify-between mb-4">
          <span className={`text-xs font-medium px-2.5 py-1 rounded-full ${STATUS_STYLES[match.status]}`}>
            {match.status}
          </span>
          <span className="text-sm text-gray-500">
            {match.date} à {match.time}
          </span>
        </div>

        <div className="flex items-center justify-between mb-4">
          <Link
            href={`/joueurs/${p1?.id}`}
            className="flex flex-col items-center gap-2 group"
          >
            <div className="w-12 h-12 rounded-full bg-emerald-100 text-emerald-700 font-bold text-sm flex items-center justify-center group-hover:bg-emerald-200 transition-colors">
              {p1?.avatar}
            </div>
            <span className="text-sm font-medium text-gray-700 group-hover:text-emerald-600">
              {p1?.name}
            </span>
          </Link>

          <div className="text-center">
            <div className="text-2xl font-bold text-gray-300">VS</div>
            {match.score && (
              <div className="text-xs font-mono text-gray-500 mt-1">{match.score}</div>
            )}
          </div>

          <Link
            href={`/joueurs/${p2?.id}`}
            className="flex flex-col items-center gap-2 group"
          >
            <div className="w-12 h-12 rounded-full bg-blue-100 text-blue-700 font-bold text-sm flex items-center justify-center group-hover:bg-blue-200 transition-colors">
              {p2?.avatar}
            </div>
            <span className="text-sm font-medium text-gray-700 group-hover:text-emerald-600">
              {p2?.name}
            </span>
          </Link>
        </div>

        <div className="pt-3 border-t border-gray-100 flex items-center gap-2 text-sm text-gray-500">
          <span>🏟️</span>
          <span>{court?.name} — {court?.neighborhood}</span>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-3xl font-bold text-gray-800 mb-1">Matchs</h1>
        <p className="text-gray-500">Suivi des matchs organisés via PadelDakar</p>
      </div>

      <section>
        <h2 className="text-xl font-bold text-gray-700 mb-4">
          À venir · <span className="text-emerald-600">{upcoming.length}</span>
        </h2>
        {upcoming.length === 0 ? (
          <div className="text-center py-12 text-gray-400 bg-white rounded-xl border border-gray-100">
            Aucun match à venir pour l&apos;instant
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
            {upcoming.map((m) => (
              <MatchCard key={m.id} matchId={m.id} />
            ))}
          </div>
        )}
      </section>

      <section>
        <h2 className="text-xl font-bold text-gray-700 mb-4">
          Terminés · <span className="text-gray-400">{past.length}</span>
        </h2>
        {past.length === 0 ? (
          <div className="text-center py-12 text-gray-400 bg-white rounded-xl border border-gray-100">
            Aucun match terminé
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
            {past.map((m) => (
              <MatchCard key={m.id} matchId={m.id} />
            ))}
          </div>
        )}
      </section>
    </div>
  );
}
