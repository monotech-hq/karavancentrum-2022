
(ns site.karavancentrum-hu.pages.main-page.frontend.sections.hero
    (:require [re-frame.api                 :as r]
              [site.components.frontend.api :as components]
              [x.app-environment.api        :as x.environment]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn hero
  []
  (let [company-name   @(r/subscribe [:db/get-item [:site :config :company-name]])
        company-slogan @(r/subscribe [:db/get-item [:site :config :company-slogan]])]
       [:div#kc-hero [:div#kc-hero--company-name-and-slogan
                       (letfn [(scroll-f [intersecting?]
                                         (x.environment/set-element-attribute! "kc-navbar--company-name-and-slogan" "data-hidden"      intersecting?)
                                         (x.environment/set-element-attribute! "kc-hero--company-name-and-slogan"   "data-hidden" (not intersecting?)))]
                              [components/scroll-sensor ::scroll-sensor {:callback-f scroll-f
                                                                         :style {:position "absolute" :top "0" :left "0"}}])
                       [:div#kc-hero--company-name   company-name]
                       [:div#kc-hero--company-slogan company-slogan]]
                     [components/scroll-icon {:style {:position :absolute :bottom 0 :left 0}}]]))

(defn view
  []
  [:section {:id :hero}
            [hero]])
