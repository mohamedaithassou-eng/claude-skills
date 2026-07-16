import Link from "next/link";
import { PLAYERS, LEVEL_COLORS, AVAILABILITY_LABELS, type Level, type Availability } from "@/lib/data";
import PlayerFilter from "@/components/PlayerFilter";

export default function JoueursPage({
  searchParams,
}: {
  searchParams: Promise<{ level?: string; availability?: string }>;
}) {
  return <JoueursContent searchParams={searchParams} />;
}

async function JoueursContent({
  searchParams,
}: {
  searchParams: Promise<{ level?: string; availability?: string }>;
}) {
  const params = await searchParams;
  const levelFilter = params.level as Level | undefined;
  const availabilityFilter = params.availability as Availability | undefined;

  const filtered = PLAYERS.filter((p) => {
    if (levelFilter && p.level !== levelFilter) return false;
    if (availabilityFilter && !p.availabilities.includes(availabilityFilter)) return false;
    return true;
  });

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-gray-800 mb-1">Joueurs</h1>
        <p className="text-gray-500">{filtered.length} joueur(s) trouvé(s)</p>
      </div>

      <PlayerFilter />

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">
        {filtered.map((player) => (
          <Link
            key={player.id}
            href={`/joueurs/${player.id}`}
            className="bg-white rounded-xl p-5 shadow-sm border border-gray-100 hover:shadow-md hover:border-emerald-200 transition-all"
          >
            <div className="flex items-center gap-4 mb-4">
              <div className="w-14 h-14 rounded-full bg-emerald-100 text-emerald-700 font-bold text-xl flex items-center justify-center flex-shrink-0">
                {player.avatar}
              </div>
              <div>
                <div className="font-semibold text-gray-800 text-lg">{player.name}</div>
                <div className="text-sm text-gray-500">{player.age} ans</div>
              </div>
            </div>

            <div className="flex flex-wrap gap-2 mb-3">
              <span className={`text-xs font-medium px-2.5 py-1 rounded-full capitalize ${LEVEL_COLORS[player.level]}`}>
                {player.level}
              </span>
              {player.availabilities.map((a) => (
                <span key={a} className="text-xs px-2.5 py-1 rounded-full bg-gray-100 text-gray-600">
                  {a}
                </span>
              ))}
            </div>

            <p className="text-sm text-gray-500 line-clamp-2">{player.bio}</p>

            <div className="mt-3 flex justify-between text-xs text-gray-400">
              <span>{player.matchesPlayed} matchs</span>
              <span>
                {player.matchesPlayed > 0
                  ? Math.round((player.wins / player.matchesPlayed) * 100)
                  : 0}
                % victoires
              </span>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
}
