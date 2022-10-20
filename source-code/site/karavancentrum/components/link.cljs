
(ns site.karavancentrum.components.link)

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn link-props-prototype
  [props text]
  (if-let [prefix (get props :prefix)]
          (merge (dissoc props prefix)
                 {:href (str prefix text)})
          props))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn link
  [props text]
  (let [link-props (link-props-prototype props text)]
       [:a.kc-link.kc-effect--underline link-props text]))

(defn view
  [{:keys [prefix] :as props} text]
  [link props text])
