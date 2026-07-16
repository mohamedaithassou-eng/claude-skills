import { COURTS } from "@/lib/data";

export default function TerrainsPage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-gray-800 mb-1">Terrains de padel à Dakar</h1>
        <p className="text-gray-500">{COURTS.length} clubs répertoriés</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
        {COURTS.map((court) => (
          <div
            key={court.id}
            id={court.id}
            className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 hover:border-emerald-200 transition-colors"
          >
            <div className="flex items-start justify-between mb-4">
              <div>
                <h2 className="text-lg font-bold text-gray-800">{court.name}</h2>
                <p className="text-sm text-gray-500">{court.neighborhood} · Dakar</p>
              </div>
              <span className="text-2xl">🏟️</span>
            </div>

            <div className="space-y-2 text-sm text-gray-600 mb-5">
              <div className="flex items-center gap-2">
                <span>📍</span>
                <span>{court.address}</span>
              </div>
              <div className="flex items-center gap-2">
                <span>🎾</span>
                <span>{court.courts} terrain{court.courts > 1 ? "s" : ""}</span>
              </div>
              <div className="flex items-center gap-2">
                <span>🕐</span>
                <span>{court.open}</span>
              </div>
              <div className="flex items-center gap-2">
                <span>💰</span>
                <span className="font-semibold text-emerald-700">
                  {court.pricePerHour.toLocaleString()} FCFA / heure
                </span>
              </div>
            </div>

            <a
              href={`tel:${court.phone}`}
              className="inline-flex items-center gap-2 text-sm font-medium text-emerald-600 hover:text-emerald-700"
            >
              <span>📞</span>
              {court.phone}
            </a>
          </div>
        ))}
      </div>

      <div className="bg-emerald-50 rounded-2xl p-6 border border-emerald-100 text-center">
        <p className="text-emerald-700 font-medium">
          Votre club n&apos;est pas listé ?
        </p>
        <p className="text-emerald-600 text-sm mt-1">
          Contactez-nous pour ajouter votre terrain.
        </p>
      </div>
    </div>
  );
}
